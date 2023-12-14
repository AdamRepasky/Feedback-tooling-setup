package com.samply.feedbackhub.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.macasaet.fernet.Key;
import com.samply.feedbackhub.BeamTask;
import com.samply.feedbackhub.BeamTaskHelper;
import com.samply.feedbackhub.ProxyResultPoller;
import com.samply.feedbackhub.exception.DoiDataAlreadyPresentException;
import com.samply.feedbackhub.model.DoiData;
import com.samply.feedbackhub.model.DoiDataDto;
import com.samply.feedbackhub.repository.DoiDataRepository;
import com.samply.feedbackhub.exception.DoiDataNotFoundException;
import com.samply.feedbackhub.service.DoiDataService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class DoiDataController {
    @Autowired
    DoiDataRepository doiDataRepository;

    @Autowired
    DoiDataService doiDataService;

    // Get all DoiData
    // only for testing
    @GetMapping("/doi-data")
    public List<DoiData> getAllDoiData() {
        return doiDataRepository.findAll();
    }

    // Create a new DoiData
    @PostMapping("/doi-data")
    public ResponseEntity<?> createDoiData(@Valid @RequestBody DoiDataDto doiDataDto) throws DoiDataAlreadyPresentException {
        // Check whether DoiData is already present in the repository
        if (doiDataRepository.findByRequest(doiDataDto.getRequestID()).size() > 0) {
            throw new DoiDataAlreadyPresentException(doiDataDto.getRequestID());
        }
        // Generate the symmetric encryption key
        final Key key = Key.generateKey();

        // Create data entity and add it to local database
        DoiData doiData = new DoiData(doiDataDto.getRequestID(), doiDataDto.getPublicationReference(), key.serialise(), UUID.randomUUID().toString());
        long doiDataID = doiDataRepository.save(doiData).getId();
        // Create and send the BeamTask to the proxy server
        BeamTask task = BeamTaskHelper.createBeamTask(doiData);
        ResponseEntity<JSONObject> responseEntity = BeamTaskHelper.sendBeamTask(task);

        if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
            return pollForTaskResults(doiDataID, task);
        } else {
            doiDataService.deleteDoiDataById(doiDataID);
            return responseEntity;
        }
    }
    // Creates a poller and awaits for results from all agent proxies
    private ResponseEntity<Object> pollForTaskResults(long doiDataID, BeamTask task) {
        CompletableFuture<HttpStatus> resultFuture = new CompletableFuture<>();
        ProxyResultPoller poller = new ProxyResultPoller(task.getId(), Integer.parseInt(System.getenv("FEEDBACK_AGENTS_COUNT")), status -> resultFuture.complete(status));
        poller.start();
        try {
            HttpStatus statusCode = resultFuture.get(); // Block and wait for the result
            if (statusCode.equals(HttpStatus.OK)) {
                doiDataService.deleteDoiDataById(doiDataID);
                return new ResponseEntity<>(statusCode);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Doi by request ID (testing only)
    @GetMapping("/doi-token/{req_id}")
    public String getDoiTokenByRequestID(@PathVariable(value = "req_id") String requestId) throws DoiDataNotFoundException {
        List<DoiData> data = doiDataRepository.findByRequest(requestId);
        if (data.size() == 0) throw new DoiDataNotFoundException(requestId);
        return data.get(0).getPublicationReferenceToken();
    }
    // Get Doi by request ID
    @GetMapping("/reference-token/{access_code}")
    public String getDoiTokenByAccessCode(@PathVariable(value = "access_code") String accessCode) throws DoiDataNotFoundException {
        List<DoiData> data = doiDataRepository.findByAccessCode(accessCode);
        if (data.size() == 0) throw new DoiDataNotFoundException(accessCode);
        return data.get(0).getPublicationReferenceToken();
    }
}

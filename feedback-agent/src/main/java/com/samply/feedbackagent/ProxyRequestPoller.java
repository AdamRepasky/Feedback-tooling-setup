package com.samply.feedbackagent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samply.feedbackagent.blaze.SpecimenExtensionUpdater;
import com.samply.feedbackagent.model.SpecimenFeedback;
import com.samply.feedbackagent.service.SpecimenFeedbackService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.macasaet.fernet.Key;
import com.macasaet.fernet.StringValidator;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/**
 * This class represents a thread that continuously polls for proxy requests,
 * processes them, and updates the relevant data.
 */
@Service
public class ProxyRequestPoller extends Thread {
    private final SpecimenFeedbackService specimenFeedbackService;
    private final SpecimenExtensionUpdater extensionUpdater;

    //private static final String BEAM_PROXY_URI = System.getenv("BEAM_PROXY_URI") + "/v1/tasks?to=app1.proxy2.broker&wait_count=1&wait_time=10s&filter=todo";
    //private static final String BLAZE_BASE_URL = System.getenv("BLAZE_BASE_URL");
    private static final String FEEDBACK_HUB_URL = System.getenv("FEEDBACK_HUB_URL") + "/reference-token/";
    private static final String BEAM_PROXY_URI = System.getenv("BEAM_PROXY_URI") + "/v1/tasks?to=" + System.getenv("PROXY_ID") + "&wait_count=1&wait_time=10s&filter=todo";
    private static final String BLAZE_BASE_URL = System.getenv("BLAZE_BASE_URL");
    private static final String APP1_SECRET = System.getenv("APP1_SECRET");
    /**
     * Constructs a new ProxyRequestPoller with the provided SpecimenFeedbackService.
     *
     * @param specimenFeedbackService The service for handling specimen feedback.
     */
    public ProxyRequestPoller(SpecimenFeedbackService specimenFeedbackService) {
        this.specimenFeedbackService = specimenFeedbackService;
        this.extensionUpdater = new SpecimenExtensionUpdater(BLAZE_BASE_URL);
    }
    /**
     * The main run method of the thread that continuously polls for proxy requests,
     * processes them, and updates the relevant data.
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "ApiKey " + System.getenv("PROXY_ID") + " " + APP1_SECRET);
                //headers.set("Authorization", "ApiKey app1.proxy2.broker App1Secret");
                HttpEntity<String> request = new HttpEntity<>(null, headers);

                ResponseEntity<String> response = restTemplate.exchange(BEAM_PROXY_URI, HttpMethod.GET, request, String.class);

                System.out.println(response);

                if (response.getStatusCode() == HttpStatus.OK) {
                    String responseBody = response.getBody();
                    JSONArray responseArray = new JSONArray(responseBody);
                    if (responseArray.length() > 0) {
                        JSONObject responseObject = responseArray.getJSONObject(0);
                        String body = responseObject.getString("body");
                        JSONObject bodyObject = new JSONObject(body);

                        String requestId = bodyObject.getString("requestId");
                        String accessCode = bodyObject.getString("accessCode");
                        String key = bodyObject.getString("key");

                        List<SpecimenFeedback> specimenFeedbacks = specimenFeedbackService.getSpecimenFeedbackByRequestID(requestId);
                        if (!specimenFeedbacks.isEmpty()) {
                            String token = getReferenceToken(accessCode);
                            Key keyObj = new Key(key);
                            final Validator<String> validator = new StringValidator() {};
                            Token tokenObj = Token.fromString(token);
                            String reference = tokenObj.validateAndDecrypt(keyObj, validator);
                            specimenFeedbackService.addPublicationReferenceToSpecimenFeedback(specimenFeedbacks, reference);
                            System.out.println("Successfully updated publication reference");

                            // propagate publication reference into blaze store
                            List<String> sampleIds = new ArrayList<>();
                            for (SpecimenFeedback specimenFeedback : specimenFeedbacks) {
                                sampleIds.add(specimenFeedback.getSampleID());
                            }
                            extensionUpdater.updateSpecimenWithExtension(sampleIds, reference);
                        } else {
                            System.out.println("RequestId of publication reference does not match any of saved Specimen RequestIds");
                        }
                        BeamResult result = new BeamResult();
                        result.setFrom("app1.proxy2.broker");
                        List to = new LinkedList<String>();
                        to.add(responseObject.getString("from"));
                        result.setTo(to);
                        result.setTask(UUID.fromString(responseObject.getString("id")));
                        result.setStatus("succeeded");
                        result.setBody("PublicationReference obtained");
                        result.setMetadata("-");
                        System.out.println(sendBeamResult(result));
                    }
                }
            } catch (Exception e) {
                System.out.println("IOException occurred: " + e.getMessage());
            }
        }
    }
    /**
     * Retrieves a reference token for a given reference code.
     *
     * @param referenceCode The reference code for which to obtain a token.
     * @return The reference token.
     */
    private String getReferenceToken(String referenceCode) {
        final String request_uri = FEEDBACK_HUB_URL + referenceCode;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(request_uri, HttpMethod.GET, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            return responseBody;
        } else {
            System.out.println("Reference token not obtained");
            return null;
        }
    }
    /**
     * Sends a BeamResult to update the status of a proxy request.
     *
     * @param result The BeamResult to send.
     * @return The ResponseEntity representing the response.
     */
    private ResponseEntity<String> sendBeamResult(BeamResult result) {
        final String request_uri = System.getenv("BEAM_PROXY_URI") + "/v1/tasks/" + result.getTask() + "/results/" + System.getenv("PROXY_ID");
        //final String request_uri = System.getenv("BEAM_PROXY_URI") + "/v1/tasks/" + result.getTask() + "/results/app1.proxy2.broker";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ApiKey " + System.getenv("PROXY_ID") + " " + APP1_SECRET);
        //headers.set("Authorization", "ApiKey app1.proxy2.broker App1Secret");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(result.buildMap(), headers);

        ResponseEntity<String> response = restTemplate.exchange(request_uri, HttpMethod.PUT, request, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response;
        } else {
            System.out.println("Could not update beam task");
            return null;
        }
    }
}

package com.samply.feedbackhub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macasaet.fernet.Key;
import com.samply.feedbackhub.BeamTask;
import com.samply.feedbackhub.model.DoiData;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

public class BeamTaskHelper {

    public static BeamTask createBeamTask(DoiData dataWithDoi) {
        BeamTask task = new BeamTask();
        task.setId(UUID.fromString(dataWithDoi.getAccessCode()));
        task.setFrom(System.getenv("FEEDBACK_HUB_BEAM_ID"));

        String agentBeamIds = System.getenv("FEEDBACK_AGENT_BEAM_IDS");
        LinkedList<String> toList = parseAgentBeamIds(agentBeamIds);
        task.setTo(toList);

        JSONObject bodyJson = buildBeamTaskBody(dataWithDoi);
        task.setBody(bodyJson.toString());

        task.setBackoffMillisecs(Integer.parseInt(System.getenv("PROXY_TASK_BACKOFF_MS")));
        task.setMaxTries(Integer.parseInt(System.getenv("PROXY_TASK_MAX_TRIES")));
        task.setTtl(System.getenv("PROXY_TASK_TTL"));
        task.setMetadata(null);

        return task;
    }

    private static LinkedList<String> parseAgentBeamIds(String agentBeamIds) {
        LinkedList<String> toList = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String[] arrayValues = objectMapper.readValue(agentBeamIds, String[].class);
            Collections.addAll(toList, arrayValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toList;
    }

    private static JSONObject buildBeamTaskBody(DoiData dataWithDoi) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("key", dataWithDoi.getSymEncKey());
        bodyJson.put("requestId", dataWithDoi.getRequestID());
        bodyJson.put("accessCode", dataWithDoi.getAccessCode());
        return bodyJson;
    }

    public static ResponseEntity<JSONObject> sendBeamTask(BeamTask task) {
        final String requestUri = System.getenv("BEAM_PROXY_URI") + "/v1/tasks";
        String beamId = System.getenv("FEEDBACK_HUB_BEAM_ID");
        String authorizationHeader = "ApiKey " + beamId + " App1Secret";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<JSONObject> request = new HttpEntity<>(task.buildJSON(), headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(requestUri, request, JSONObject.class);
    }
}

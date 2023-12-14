package com.samply.feedbackhub;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class ProxyResultPoller extends Thread {
    private final UUID taskUUID;
    private final int waitCount;
    private final ProxyResultListener resultListener;
    /**
     * Constructs a new {@code ProxyResultPoller} with the specified parameters.
     *
     * @param taskUUID       The unique identifier of the task to poll.
     * @param waitCount      The number of times to wait for the result.
     * @param resultListener The listener to be notified about the result.
     */
    public ProxyResultPoller(UUID taskUUID, int waitCount, ProxyResultListener resultListener) {
        this.taskUUID = taskUUID;
        this.waitCount = waitCount;
        this.resultListener = resultListener;
    }
    /**
     * Runs the thread, sending a GET request to the Beam proxy and notifying
     * the result listener based on the HTTP status code of the response.
     * It waits until all adressed agent proxies return a result
     */
    @Override
    public void run() {
        final String requestUri = System.getenv("BEAM_PROXY_URI") + "/v1/tasks/" + taskUUID + "/results?wait_count=" + waitCount;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ApiKey " + System.getenv("FEEDBACK_HUB_BEAM_ID") + " " + System.getenv("APP1_SECRET"));
        //headers.set("Authorization", "ApiKey app1.proxy1.broker App1Secret");
        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            resultListener.onResult(HttpStatus.OK);
        } else {
            resultListener.onResult(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

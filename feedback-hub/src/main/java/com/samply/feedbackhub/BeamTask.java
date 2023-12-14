package com.samply.feedbackhub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.UUID;

public class BeamTask {
    UUID id;
    String from;
    List<String> to;
    String body;

    //for failure strategy object
    //retry object
    int backoffMillisecs;
    int maxTries;
    String ttl;
    String metadata;
    public BeamTask() {}

    public BeamTask(UUID id, String from, List<String> to, String body, int backoffMillisecs, int maxTries, String ttl, String metadata) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.body = body;
        this.backoffMillisecs = backoffMillisecs;
        this.maxTries = maxTries;
        this.ttl = ttl;
        this.metadata = metadata;
    }

    public JSONObject buildJSON() {
        JSONObject beamProxyTask = new JSONObject();
        beamProxyTask.put("id", this.id);
        beamProxyTask.put("from", this.from);

        JSONArray toArray = new JSONArray();
        toArray.addAll(this.to);
        beamProxyTask.put("to", toArray);
        beamProxyTask.put("body", this.body);

        JSONObject failureStrategyObject = new JSONObject();
        JSONObject retryObject = new JSONObject();
        retryObject.put("backoff_millisecs", this.backoffMillisecs);
        retryObject.put("max_tries", this.maxTries);
        failureStrategyObject.put("retry", retryObject);
        beamProxyTask.put("failure_strategy", failureStrategyObject);

        beamProxyTask.put("ttl", this.ttl);
        beamProxyTask.put("metadata", this.metadata);

        return beamProxyTask;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getBackoffMillisecs() {
        return backoffMillisecs;
    }

    public void setBackoffMillisecs(int backoffMillisecs) {
        this.backoffMillisecs = backoffMillisecs;
    }

    public int getMaxTries() {
        return maxTries;
    }

    public void setMaxTries(int maxTries) {
        this.maxTries = maxTries;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}

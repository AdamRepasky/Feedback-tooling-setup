package com.samply.feedbackagent;

import java.util.*;

public class BeamResult {
    String from;
    List<String> to;
    UUID task;
    String status;
    String body;
    String metadata;

    public BeamResult() {}

    public BeamResult(String from, List<String> to, UUID task, String status, String body, String metadata) {
        this.from = from;
        this.to = to;
        this.task = task;
        this.status = status;
        this.body = body;
        this.metadata = metadata;
    }

    public Map<String, Object> buildMap() {
        Map<String, Object> beamResultMap = new HashMap<>();
        beamResultMap.put("from", this.from);

        List<String> toList = new ArrayList<>(this.to);
        beamResultMap.put("to", toList);

        beamResultMap.put("task", this.task.toString());
        beamResultMap.put("status", this.status);
        beamResultMap.put("body", this.body);
        beamResultMap.put("metadata", this.metadata);

        return beamResultMap;
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

    public UUID getTask() {
        return task;
    }

    public void setTask(UUID task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
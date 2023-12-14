package com.samply.feedbackhub.model;

public class DoiDataDto {
    private String requestID;
    private String publicationReference;

    public DoiDataDto(String requestID, String publicationReference) {
        this.requestID = requestID;
        this.publicationReference = publicationReference;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getPublicationReference() {
        return publicationReference;
    }

    public void setPublicationReference(String publicationReference) {
        this.publicationReference = publicationReference;
    }
}
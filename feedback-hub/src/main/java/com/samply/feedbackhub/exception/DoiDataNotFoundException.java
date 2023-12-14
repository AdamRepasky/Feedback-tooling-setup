package com.samply.feedbackhub.exception;

public class DoiDataNotFoundException extends Exception {
    public DoiDataNotFoundException(String specimenFeedbackID) {
        super(String.format("Specimen feedback is not found with id : '%s'", specimenFeedbackID));
    }
}
package com.samply.feedbackhub.exception;

public class DoiDataAlreadyPresentException extends Exception {
    public DoiDataAlreadyPresentException(String specimenFeedbackID) {
        super(String.format("Specimen feedback with id '%s' already present", specimenFeedbackID));
    }
}
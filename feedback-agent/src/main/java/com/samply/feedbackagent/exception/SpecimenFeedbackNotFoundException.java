package com.samply.feedbackagent.exception;

public class SpecimenFeedbackNotFoundException extends Exception {
    public SpecimenFeedbackNotFoundException(long specimenFeedbackID) {
        super(String.format("Specimen feedback is not found with id : '%s'", specimenFeedbackID));
    }
}
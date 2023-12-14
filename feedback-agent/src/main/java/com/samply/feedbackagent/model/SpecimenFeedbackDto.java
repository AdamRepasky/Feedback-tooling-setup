package com.samply.feedbackagent.model;

import java.util.List;

public class SpecimenFeedbackDto {
    private List<SpecimenFeedback> feedbackList;

    public SpecimenFeedbackDto() {
        super();
    }

    public SpecimenFeedbackDto(List<SpecimenFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<SpecimenFeedback> getFeedbackList() {
        return this.feedbackList;
    }

    public void setFeedbackList(List<SpecimenFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}

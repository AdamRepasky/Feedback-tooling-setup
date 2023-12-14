package com.samply.feedbackagent.service;

import com.samply.feedbackagent.exception.SpecimenFeedbackNotFoundException;
import com.samply.feedbackagent.model.SpecimenFeedback;
import com.samply.feedbackagent.repository.SpecimenFeedbackRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SpecimenFeedbackService {

    private final SpecimenFeedbackRepository specimenFeedbackRepository;

    public SpecimenFeedbackService(SpecimenFeedbackRepository specimenFeedbackRepository) {
        this.specimenFeedbackRepository = specimenFeedbackRepository;
    }

    public List<SpecimenFeedback> getSpecimenFeedbackByRequestID(String requestId) {
        return specimenFeedbackRepository.findByRequest(requestId);
    }

    public List<SpecimenFeedback> addPublicationReferenceToSpecimenFeedback(List<SpecimenFeedback> specimenFeedbacks, String publicationReference) {

        for (SpecimenFeedback specimenFeedback : specimenFeedbacks) {
            specimenFeedback.setPublicationReference(publicationReference);
        }
        return specimenFeedbackRepository.saveAll(specimenFeedbacks);
    }
}

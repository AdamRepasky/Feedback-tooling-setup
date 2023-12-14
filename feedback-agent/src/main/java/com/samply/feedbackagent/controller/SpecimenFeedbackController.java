package com.samply.feedbackagent.controller;
import com.samply.feedbackagent.model.SpecimenFeedbackDto;
import com.samply.feedbackagent.repository.SpecimenFeedbackRepository;
import com.samply.feedbackagent.exception.SpecimenFeedbackNotFoundException;
import com.samply.feedbackagent.model.SpecimenFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.List;

@RestController
public class SpecimenFeedbackController {
    @Autowired
    SpecimenFeedbackRepository specimenFeedbackRepository;

    // Get all SpecimenFeedback
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/specimen-feedback")
    public List<SpecimenFeedback> getAllSpecimenFeedback() {
        return specimenFeedbackRepository.findAll();
    }

    // Create a new SpecimenFeedback
    @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping("/specimen-feedback")
    public SpecimenFeedback createSpecimenFeedback(@Valid @RequestBody SpecimenFeedback specimenFeedback) {
        return specimenFeedbackRepository.save(specimenFeedback);
    }
    // Create multiple SpecimenFeedback
    @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping("/multiple-specimen-feedback")
    public List<SpecimenFeedback> createSpecimenFeedback(@Valid @RequestBody SpecimenFeedbackDto specimenFeedbackDto) {
        return specimenFeedbackRepository.saveAll(specimenFeedbackDto.getFeedbackList());
    }

    // Get a Single SpecimenFeedback
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/specimen-feedback/{id}")
    public SpecimenFeedback getSpecimenFeedbackById(@PathVariable(value = "id") Long specimenFeedbackId) throws SpecimenFeedbackNotFoundException {
        return specimenFeedbackRepository.findById(specimenFeedbackId)
                .orElseThrow(() -> new SpecimenFeedbackNotFoundException(specimenFeedbackId));
    }
}

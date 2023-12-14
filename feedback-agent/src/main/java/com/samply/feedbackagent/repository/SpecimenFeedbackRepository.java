package com.samply.feedbackagent.repository;

import com.samply.feedbackagent.model.SpecimenFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecimenFeedbackRepository extends JpaRepository<SpecimenFeedback, Long> {
    @Query(nativeQuery= true, value="SELECT * FROM specimen_feedback WHERE request_id = ?")
    List<SpecimenFeedback> findByRequest(String id);
}
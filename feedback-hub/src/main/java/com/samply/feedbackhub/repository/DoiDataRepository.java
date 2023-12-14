package com.samply.feedbackhub.repository;

import com.samply.feedbackhub.model.DoiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoiDataRepository extends JpaRepository<DoiData, Long> {
    @Query(nativeQuery= true, value="SELECT * FROM doi_data WHERE request_id = ?")
    List<DoiData> findByRequest(String id);

    @Query(nativeQuery= true, value="SELECT * FROM doi_data WHERE access_code = ?")
    List<DoiData> findByAccessCode(String id);
}
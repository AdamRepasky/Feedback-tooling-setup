package com.samply.feedbackhub.service;

import com.samply.feedbackhub.repository.DoiDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoiDataService {

    private final DoiDataRepository doiDataRepository;

    @Autowired
    public DoiDataService(DoiDataRepository doiDataRepository) {
        this.doiDataRepository = doiDataRepository;
    }

    public void deleteDoiDataById(long id) {
        doiDataRepository.deleteById(id);
    }
}
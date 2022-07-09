package com.example.demo.service;

import com.example.demo.model.entity.ClinicalRecommendationEntity;
import com.example.demo.repository.clinrec_repository.ClinRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinRecService {
    @Autowired
    ClinRecRepository repository;
    public List<ClinicalRecommendationEntity> findClinRecByMkb(String mkb) {
        return repository.findClinRecByMkb(mkb);
    }
}

package com.example.dochelper.service;

import com.example.dochelper.model.entity.ClinicalRecommendationEntity;
import com.example.dochelper.repository.clinrec_repository.ClinRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ClinRecService {
    @Autowired
    ClinRecRepository repository;

    public List<ClinicalRecommendationEntity> findClinRecByMkb(String mkb) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(clinicalRecommendationEntity -> clinicalRecommendationEntity
                        .getMkb().contains(mkb))
                .toList();
    }
}

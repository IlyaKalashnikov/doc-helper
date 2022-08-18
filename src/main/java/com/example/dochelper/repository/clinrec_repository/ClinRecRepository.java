package com.example.dochelper.repository.clinrec_repository;

import com.example.dochelper.model.entity.ClinicalRecommendationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ClinRecRepository extends CrudRepository<ClinicalRecommendationEntity, String> {
}

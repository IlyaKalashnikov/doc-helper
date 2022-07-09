package com.example.demo.repository.clinrec_repository;

import com.example.demo.model.entity.ClinicalRecommendationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinRecRepository extends CrudRepository<ClinicalRecommendationEntity, String> {
    @Query(value = "SELECT * FROM recommendations WHERE mkb @> '\":mkb\"'", nativeQuery = true)
    List<ClinicalRecommendationEntity> findClinRecByMkb(@Param(value = "mkb")String mkb);
}

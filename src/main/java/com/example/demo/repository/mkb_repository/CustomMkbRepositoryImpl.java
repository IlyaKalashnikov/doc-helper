package com.example.demo.repository.mkb_repository;

import com.example.demo.model.entity.MkbEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomMkbRepositoryImpl implements CustomMkbRepository {
    @Autowired
    EntityManager entityManager;

    @Override
    public List<MkbEntity> findMkbByDisease(String disease) {
        TypedQuery<MkbEntity> typedQuery = entityManager.createQuery(
                "SELECT e FROM MkbEntity e WHERE e.disease LIKE :disease",
                MkbEntity.class
        );
        typedQuery.setParameter("disease", "%" + disease + "%");

        return typedQuery.getResultList();
    }
}

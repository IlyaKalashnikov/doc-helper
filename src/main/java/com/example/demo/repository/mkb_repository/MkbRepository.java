package com.example.demo.repository.mkb_repository;

import com.example.demo.model.entity.MkbEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MkbRepository extends CrudRepository<MkbEntity, String>, CustomMkbRepository {
}

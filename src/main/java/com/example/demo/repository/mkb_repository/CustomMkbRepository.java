package com.example.demo.repository.mkb_repository;

import com.example.demo.model.entity.MkbEntity;

import java.util.List;

public interface CustomMkbRepository {

    List<MkbEntity> findMkbByDisease(String disease);
}

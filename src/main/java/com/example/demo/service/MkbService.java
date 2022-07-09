package com.example.demo.service;

import com.example.demo.repository.mkb_repository.MkbRepository;
import com.example.demo.model.entity.MkbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MkbService {
    @Autowired
    MkbRepository mkbRepository;
    public List<MkbEntity> findMkbByDisease (String disease){
        return mkbRepository.findMkbByDisease(disease);
    }
}

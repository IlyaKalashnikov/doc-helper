package com.example.demo.controller;

import com.example.demo.model.entity.MkbEntity;
import com.example.demo.service.MkbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/mkb")
public class MkbController {
    @Autowired
    public MkbService service;

    @GetMapping(path = "/{disease}")
    public List<MkbEntity> findMkbByDisease(@PathVariable("disease") String disease) {
        return service.findMkbByDisease(disease);
    }
}

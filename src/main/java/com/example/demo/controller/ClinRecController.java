package com.example.demo.controller;

import com.example.demo.model.entity.ClinicalRecommendationEntity;
import com.example.demo.service.ClinRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/clinrec")
public class ClinRecController {
    @Autowired
    ClinRecService clinRecService;

    @GetMapping(path = "/{mkb}")
    public List<ClinicalRecommendationEntity> findClinRecByMkb(@PathVariable("mkb") String mkb) {
        return clinRecService.findClinRecByMkb(mkb);
    }
}

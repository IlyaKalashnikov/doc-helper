package com.example.dochelper.controller;

import com.example.dochelper.service.ClinRecContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/clinrec")
public class ClinRecController {

    private final ClinRecContentService clinRecContentService;

    public ClinRecController(ClinRecContentService clinRecContentService) {
        this.clinRecContentService = clinRecContentService;
    }

    @GetMapping
    public String showClinRec (Model model, @RequestParam String title, @RequestParam String id) {
        model.addAttribute("clinRecContent", clinRecContentService.getClinRecContentByTitle(title, id));
        return "clinRec";
    }
}

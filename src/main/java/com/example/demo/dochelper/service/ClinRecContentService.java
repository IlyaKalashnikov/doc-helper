package com.example.demo.dochelper.service;

import com.example.demo.dochelper.client.ClinRecContentClient;
import com.example.demo.dochelper.mapper.DocHelperMapper;
import com.example.demo.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.demo.dochelper.model.entity.clinrec_content.ClinRecContent;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClinRecContentService {
    private final ClinRecContentClient client;
    private final DocHelperMapper mapper;

    public ClinRecContentService(ClinRecContentClient client, DocHelperMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public List<ClinRecContent> getClinRecContentsById(String id){
        ClinRecContentDto dto = client.getClinRecDtoById(id);
        while (dto == null){
            dto = client.getClinRecDtoById(id);
        }
        return mapper.clinRecContentDtoToEntity(dto);
    }

    public String getClinRecContentByTitle (String title, String id) {
        List<ClinRecContent> clinRecContents = getClinRecContentsById(id);
        ClinRecContent clinRecText = clinRecContents.stream()
                .filter(clinRecContent -> clinRecContent.getTitle().contains(title))
                .findFirst()
                .orElse(null);
        return clinRecText.getContent();
    }
}

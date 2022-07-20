package com.example.demo.dochelper.service;

import com.example.demo.dochelper.mapper.DocHelperMapper;
import com.example.demo.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.demo.dochelper.model.entity.clinrec_content.ClinRecContent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ClinRecContentService {
    private final DocHelperMapper mapper;
    private final WebClient webClient;

    public ClinRecContentService(DocHelperMapper mapper,
                                 WebClient.Builder webClientBuilder) {
        this.mapper = mapper;
        this.webClient = webClientBuilder
                .baseUrl("https://apicr.minzdrav.gov.ru/")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }

    public List<ClinRecContent> getClinRecContentsById(String id) {
        ClinRecContentDto clinRecContentDto = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api.ashx")
                        .queryParam("op", "GetClinrec2")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(ClinRecContentDto.class)
                .block();
        return mapper.clinRecContentDtoToEntity(clinRecContentDto);
    }

    public String getClinRecContentByTitle(String title, String id) {
        List<ClinRecContent> clinRecContents = getClinRecContentsById(id);
        ClinRecContent clinRecText = clinRecContents.stream()
                .filter(clinRecContent -> clinRecContent.getTitle().contains(title))
                .findFirst()
                .orElse(null);
        return clinRecText.getContent();
    }
}

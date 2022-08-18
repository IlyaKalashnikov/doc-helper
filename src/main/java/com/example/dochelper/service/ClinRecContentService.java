package com.example.dochelper.service;

import com.example.dochelper.mapper.DocHelperMapper;
import com.example.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.dochelper.model.entity.clinrec_content.ClinRecContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class ClinRecContentService {
    private final DocHelperMapper mapper;

    private final String baseUrl;
    private final WebClient webClient;

    public ClinRecContentService(DocHelperMapper mapper,
                                 WebClient.Builder webClientBuilder, @Value("${minzdrav.request.url}") String baseUrl) {
        this.baseUrl=baseUrl;
        this.mapper = mapper;
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }
    public Flux<ClinRecContent> getClinRecContentsById(String id) {
        Mono<ClinRecContentDto> clinRecContentDto = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api.ashx")
                        .queryParam("op", "GetClinrec2")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(ClinRecContentDto.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(1))
                                .filter(throwable -> throwable instanceof java.net.SocketException)
                );
        return mapper.clinRecContentDtoToEntity(clinRecContentDto);
    }

    public String getClinRecContentByTitle(String title, String id) {
        Flux<ClinRecContent> clinRecContents = getClinRecContentsById(id);
        ClinRecContent clinRecText = clinRecContents.toStream()
                .filter(clinRecContent -> clinRecContent.getTitle().contains(title))
                .findFirst()
                .orElse(null);
        return clinRecText.getContent();
    }
}

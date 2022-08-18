package com.example.dochelper.service_test;

import com.example.dochelper.mapper.DocHelperMapper;
import com.example.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.dochelper.model.entity.clinrec_content.ClinRecContent;
import com.example.dochelper.service.ClinRecContentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;


public class ClinRecContentServiceTest {
    ClinRecContentService clinRecContentService;
    DocHelperMapper docHelperMapper = mock(DocHelperMapper.class);
    ObjectMapper objectMapper = new ObjectMapper();
    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void shutDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void getClinRecContentsById_shouldSendGetRequestAndReturnEntityObject() throws IOException {
        WebClient.Builder webclientBuilder = WebClient.builder();
        clinRecContentService = new ClinRecContentService(docHelperMapper, webclientBuilder, String.format("http://localhost:%s",
                mockBackEnd.getPort()));

        ClinRecContentDto clinRecContentDto = new ClinRecContentDto();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(clinRecContentDto))
                .addHeader("Content-Type", "application/json"));

        when(docHelperMapper.clinRecContentDtoToEntity(any())).thenReturn
                (Flux.just(ClinRecContent.builder()
                        .content("Content")
                        .title("Title")
                        .build()));

        Flux<ClinRecContent> clinRecContentFlux = clinRecContentService.getClinRecContentsById("anyId");

        assertThat(clinRecContentFlux).isNotNull();
        assertThat(clinRecContentFlux.toStream()
                .filter(clinRecContent -> clinRecContent.getContent().contains("Content"))
                .collect(Collectors.toList())).isNotEmpty();
        assertThat(clinRecContentFlux.toStream()
                .filter(clinRecContent -> clinRecContent.getTitle().contains("Title"))
                .collect(Collectors.toList())).isNotEmpty();
    }

    @Test
    public void getClinRecContentByTitle_shouldReturnContentForGivenTitle() throws JsonProcessingException {
        WebClient.Builder webclientBuilder = WebClient.builder();
        clinRecContentService = new ClinRecContentService(docHelperMapper, webclientBuilder, String.format("http://localhost:%s",
                mockBackEnd.getPort()));

        ClinRecContentDto clinRecContentDto = new ClinRecContentDto();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(clinRecContentDto))
                .addHeader("Content-Type", "application/json"));

        when(docHelperMapper.clinRecContentDtoToEntity(any())).thenReturn
                (Flux.just(ClinRecContent.builder()
                        .content("Content")
                        .title("Title")
                        .build()));

        String actual = clinRecContentService.getClinRecContentByTitle("Title", "anyId");

        assertThat(actual).isEqualTo("Content");
    }
}

package com.example.dochelper.controller_test;

import com.example.dochelper.controller.ClinRecController;
import com.example.dochelper.model.entity.clinrec_content.ClinRecContent;
import com.example.dochelper.service.ClinRecContentService;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest (ClinRecController.class)
public class ClinRecControllerTest {
    @MockBean
    ClinRecContentService clinRecContentService;
    @Autowired
    MockMvc mvc;

    @Test
    public void controllerShouldAdressRelevantModel_modelShouldContainRelevantContent() throws Exception {
        ClinRecContent clinRecContent = ClinRecContent.builder()
                .content("Content")
                .title("Title")
                .build();

        when(clinRecContentService.getClinRecContentByTitle(anyString(),anyString()))
                .thenReturn(clinRecContent.getContent());

        mvc.perform(MockMvcRequestBuilders.get("/api/clinrec")
                        .param("title",anyString())
                        .param("id", anyString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("clinRec"))
                .andExpect(MockMvcResultMatchers.model().attribute("clinRecContent", "Content"));

    }

}

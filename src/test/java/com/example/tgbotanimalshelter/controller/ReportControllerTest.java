package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    private static final String ROOT = "/report";

    @Test
    void findByIdTest() throws Exception {
        long id = 1L;

        mockMvc.perform(get(ROOT + "/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(reportService).findById(id);
    }
}
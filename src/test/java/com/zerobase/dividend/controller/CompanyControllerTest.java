package com.zerobase.dividend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.dividend.dto.AddCompanyDto;
import com.zerobase.dividend.dto.CompanyDto;
import com.zerobase.dividend.service.CompanyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {
    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회사 추가 성공")
    void successAddCompany() throws Exception {
        //given
        given(companyService.addCompany(anyString()))
                .willReturn(CompanyDto.builder()
                        .name("testCompany")
                        .ticker("tst")
                        .build());
        //when
        //then
        mockMvc.perform(post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AddCompanyDto.Request("tst")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testCompany"))
                .andExpect(jsonPath("$.ticker").value("tst"))
                .andDo(print());
    }
}
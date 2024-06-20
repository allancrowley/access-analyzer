package com.access.controller;

import com.access.service.EmailProviderService;
import com.access.util.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.access.UrlConstants.EMAIL_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmailProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmailProviderService emailProviderService;

    @Test
    @DisplayName("Test get services emails")
    public void givenListOfServices_whenGettingServicesEmails_thenSuccessResponse() throws Exception {
        //given
        List<String> serviceNames = DataUtils.getServiceNamesList();
        List<String> expectedEmails = DataUtils.getEmailsList();
        BDDMockito.given(emailProviderService.getServicesEmails(anyList()))
                .willReturn(expectedEmails);
        // When
        ResultActions result = mockMvc.perform(get("/" + EMAIL_PATH)
                .param("services", serviceNames.toArray(new String[0])));
        // Then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(expectedEmails.get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4]").value(expectedEmails.get(4)));
    }
}

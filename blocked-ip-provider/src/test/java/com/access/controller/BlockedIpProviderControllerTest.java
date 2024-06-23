package com.access.controller;

import com.access.service.BlockedIpProviderService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.access.UrlConstants.EMAIL_PATH;
import static com.access.UrlConstants.IP_PATH;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BlockedIpProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BlockedIpProviderService blockedIpProviderService;

    @Test
    @DisplayName("Test get blocked ips")
    public void givenRequestWhenGetIpsThenListIpsReturns() throws Exception {
        //given
        List<String> expectedIps = DataUtils.getSubnetList();
        BDDMockito.given(blockedIpProviderService.getBlockedList()).
                willReturn(expectedIps);
        // When
        ResultActions result = mockMvc.perform(get("/" + IP_PATH));

        // Then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(expectedIps.get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4]").value(expectedIps.get(4)));
    }
}



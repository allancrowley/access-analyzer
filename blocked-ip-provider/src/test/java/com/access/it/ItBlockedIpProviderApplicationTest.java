package com.access.it;

import com.access.model.IpSubnetEntity;
import com.access.repo.AccessDb;
import com.access.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.access.UrlConstants.EMAIL_PATH;
import static com.access.UrlConstants.IP_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
public class ItBlockedIpProviderApplicationTest extends AbstractBlockedIpProviderBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccessDb accessDb;

    @Test
    @DisplayName("Test get services emails using test containers")
    public void givenServiceNames_whenFindEmails_thenEmailsAreReturned() throws Exception {
        //given
        List<String> expectedIps = DataUtils.getSubnetList();
        List<String> ips = accessDb.getBlockedList();
        assertTrue(ips.isEmpty());
        List<IpSubnetEntity> ipSubnetEntities = DataUtils.getIpSubnetEntitiesList();
        accessDb.saveAll(ipSubnetEntities);
        //when
        ResultActions result = mockMvc.perform(get("/" + IP_PATH));
        //then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(expectedIps.get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4]").value(expectedIps.get(4)));
    }
}


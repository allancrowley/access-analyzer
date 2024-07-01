package com.access.it;

import com.access.model.EmailEntity;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Testcontainers
@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
public class ItEmailProviderApplicationTest extends AbstractEmailProviderBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccessDb accessDb;

    @Test
    @DisplayName("Test get services emails using test containers")
    public void givenServiceNames_whenFindEmails_thenEmailsAreReturned() throws Exception {
        //given
        List<String> serviceNames = DataUtils.getServiceNamesList();
        List<String> expectedEmails = DataUtils.getEmailsList();
        List<EmailEntity> emailEntities = DataUtils.getEmailEntitiesList();
        accessDb.saveAll(emailEntities);
        //when
        ResultActions result = mockMvc.perform(get("/" + EMAIL_PATH)
                .param("services", serviceNames.toArray(new String[0])));
        //then
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(expectedEmails.get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4]").value(expectedEmails.get(4)));
    }
}

package com.access;

import com.access.dto.AuthFailureDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.result.*;
import static com.access.UrlConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestChannelBinderConfiguration.class)
class AuthFailureBrokerPopulatorApplicationTests {
    @Autowired
    OutputDestination consumer;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${spring.cloud.stream.bindings.auth-failure-out-1.destination}")
    String bindingName;

    @Test
    @DisplayName("Test routing DTO to message broker functionality")
    void givenAuthFailureDto_whenDtoReceivedByController_thenDtoRoutedToMessageBroker() throws Exception {
        //given
        AuthFailureDto authFailureDto = new AuthFailureDto("100.100.100", 1L, "myservice");
        String jsonToSend = objectMapper.writeValueAsString(authFailureDto);
        //when
        ResultActions result = mockMvc.perform(post("/" + AUTH_FAILURE_ROUTING_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(jsonToSend));
        //then
        result.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
        Message message = consumer.receive(10, bindingName);
        String receivedJson = new String ((byte[]) message.getPayload());
        assertThat(receivedJson).isEqualTo(jsonToSend);
    }

}

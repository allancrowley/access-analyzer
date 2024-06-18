package com.access.controller;



import com.access.IpAnalyzerApplication;
import com.access.dto.AttackAttemptDto;
import com.access.dto.AuthFailureDto;
import com.access.service.IpAnalyzerService;
import com.access.util.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest

@Import(TestChannelBinderConfiguration.class)
public class BrokerMessengerTest  {

    @Value("${app.analyzer.producer.binding.name}")
    private String producerBindingName;
    @MockBean
    IpAnalyzerService ipAnalyzerService;

    @Autowired
    InputDestination producer;

    @Autowired
    OutputDestination consumer;

    @Autowired
    private IpAnalyzerApplication application;


    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Test kafka send AuthFailureDto")
    void givenAuthFailureDto_whenKafkaSendAuthFailureDto_thenOk() throws IOException  {
        //given
        AuthFailureDto authFailureDto = DataUtils.getExistsAuthFailureDto();
        //when
        producer.send(new GenericMessage<>(authFailureDto), "auth-failure");
        //then
        verify(ipAnalyzerService, times(1)).processAuthFailure(any(AuthFailureDto.class));
    }

    @Test
    @DisplayName("Test processAuthFailure cold and  send message")
    void givenAuthFailureDto_whenIpAnalyzerServiceCold_thenAttackAttemptDtoReturnedAndMessageSend() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //given
        AuthFailureDto authFailureDto = DataUtils.getExistsAuthFailureDto();
        AttackAttemptDto attackAttemptDto = DataUtils.getAttackAttemptDto();
        //when
        when(ipAnalyzerService.processAuthFailure(authFailureDto)).thenReturn(attackAttemptDto);
        producer.send(new GenericMessage<>(authFailureDto), "auth-failure");
        Message<byte[]> message = consumer.receive(10, "attack-out-0");
        AttackAttemptDto dto = objectMapper.readValue(message.getPayload(), AttackAttemptDto.class);
        //then
        assertEquals("100.100.100", dto.subnet());
        assertNotNull(message);
    }
}


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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import org.springframework.messaging.support.GenericMessage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.assertEquals;
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





    @Test
    @DisplayName("Test kafka send AuthFailureDto")
    void kafkaSendAuthFailureDto()  {
        AuthFailureDto authFailureDto = DataUtils.getFullMapAuthFailureDto();
        producer.send(new GenericMessage<>(authFailureDto));
        verify(ipAnalyzerService, times(1)).processAuthFailure(any(AuthFailureDto.class));

    }

    @Test
    @DisplayName("Test processAuthFailure cold and  send message")
    void processAuthFailureColdAndSendMessage() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = IpAnalyzerApplication.class.getDeclaredMethod("process-AuthFailure");
        method.setAccessible(true);
        AuthFailureDto authFailureDto = DataUtils.getExistsAuthFailureDto();
        method.invoke(application, authFailureDto);
        var message = this.consumer.receive(10, producerBindingName);
        ObjectMapper objectMapper = new ObjectMapper();
        AttackAttemptDto attackAttemptDto = objectMapper.readValue(message.getPayload(), AttackAttemptDto.class);
        String actualSubnet = attackAttemptDto.subnet();
        assertEquals("100.100.100", actualSubnet);
    }


}


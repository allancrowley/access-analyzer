package com.access.controller;


import com.access.dto.AuthFailureDto;
import com.access.service.IpAnalyzerService;
import com.access.util.DataUtils;
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
import org.springframework.messaging.support.GenericMessage;
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




    @Test
    @DisplayName("Test amount of attempt more then threshold value then message send")
    void givenAuthFailureDto_whenAmountOfAttemptMoreThenThresholdValue_thenMessage()  {
        AuthFailureDto authFailureDto = DataUtils.getFullMapAuthFailureDto();
        producer.send(new GenericMessage<>(authFailureDto));
        verify(ipAnalyzerService, times(1)).processAuthFailure(any(AuthFailureDto.class));

    }
}


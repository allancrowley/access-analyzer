package com.access.controller;

import com.access.dto.AttackAttemptDto;
import com.access.service.BlockingListPopulatorService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class BlockingListPopulatorControllerTests {

    @MockBean
    BlockingListPopulatorService blockingListPopulatorService;

    @Autowired
    InputDestination producer;


    //FIXME
    String bindingName = "blockingListPopulatorConsumer-in-0";

    @Test
    @DisplayName("Test consuming attack attempts from message broker functionality")
    public void givenAttackAttemptDto_whenAttackAttemptDtoIsSent_thenServiceMethodCalled() {
        //given
        AttackAttemptDto attackAttemptToBeSent = DataUtils.getAttackAttemptDto();
        //when
        producer.send(new GenericMessage<AttackAttemptDto>(attackAttemptToBeSent), bindingName);
        //then
        verify(blockingListPopulatorService, times(1)).addAttackAttemptDto(attackAttemptToBeSent);
    }


}

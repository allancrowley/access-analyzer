package com.access.controller;

import com.access.dto.AttackAttemptDto;
import com.access.service.BlockingListPopulatorService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class BlockingListPopulatorControllerTests {

    @MockBean
    BlockingListPopulatorService blockingListPopulatorService;

    @Autowired
    InputDestination producer;


    //FIXME
    String bindingName = "attack-attempt";

    @Test
    @DisplayName("Test consuming attack attempts from message broker functionality")
    public void givenAttackAttemptDto_whenAttackAttemptDtoIsSent_thenServiceMethodCalled() {
        //given
        AttackAttemptDto attackAttemptToBeSent = DataUtils.getAttackAttemptDtoServiceNotExists();
        //when
        producer.send(new GenericMessage<AttackAttemptDto>(attackAttemptToBeSent), bindingName);
        //then
        verify(blockingListPopulatorService, times(1)).addAttackAttemptDto(attackAttemptToBeSent);
    }


}

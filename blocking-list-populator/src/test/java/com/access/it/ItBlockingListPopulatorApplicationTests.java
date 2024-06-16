package com.access.it;

import com.access.dto.AttackAttemptDto;
import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;
import com.access.repo.AccessBD;
import com.access.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(TestChannelBinderConfiguration.class)
@Testcontainers
@Sql(scripts = "classpath:test_data.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItBlockingListPopulatorApplicationTests extends AbstractBlockingListPopulatorBaseTest {

    @Autowired
    InputDestination producer;

    @Spy
    AccessBD accessBD;

    //FIXME
    String bindingName = "blockingListPopulatorConsumer-in-0";

    @Test
    @DisplayName("Test consume attack attempt with ip subnet already in DB functionality")
    public void givenAttackAttemptWithSubnetAlreadyInDB_whenAddAttackAttempt_thenNothingAdded() throws Exception {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoAlreadyAdded();
        //when
        producer.send(new GenericMessage<AttackAttemptDto>(attemptToSave), bindingName);
        Thread.sleep(100);
        //then
        verify(accessBD, times(0)).save(any(IpSubnetEntity.class));
        verify(accessBD, times(0)).save(any(AttackAttemptEntity.class));
    }

    @Test
    @DisplayName("Test consume attack attempt with few same service names functionality")
    public void givenAttackAttemptWithSameServices_whenAddAttackAttempt_thenOneServiceAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoSameServices();
        //when
        producer.send(new GenericMessage<AttackAttemptDto>(attemptToSave), bindingName);
        //then
        verify(accessBD, times(1)).save(any(IpSubnetEntity.class));
        verify(accessBD, times(1)).save(any(AttackAttemptEntity.class));
    }

    @Test
    @DisplayName("Test consume attack attempt with different service names functionality")
    public void givenAttackAttemptWithDifferentServices_whenAddAttackAttempt_thenAllServicesAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoDifferentServices();
        //when
        producer.send(new GenericMessage<AttackAttemptDto>(attemptToSave), bindingName);
        //then
        verify(accessBD, times(1)).save(any(IpSubnetEntity.class));
        verify(accessBD, times(attemptToSave.services().size())).save(any(AttackAttemptEntity.class));
    }
}

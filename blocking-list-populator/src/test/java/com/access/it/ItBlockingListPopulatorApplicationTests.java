package com.access.it;

import com.access.dto.*;
import com.access.model.*;
import com.access.repo.AccessBD;
import com.access.util.DataUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

@Import(TestChannelBinderConfiguration.class)
@Testcontainers
@Sql(scripts = "classpath:test_data.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItBlockingListPopulatorApplicationTests extends AbstractBlockingListPopulatorBaseTest {

    @Autowired
    InputDestination producer;

    @Autowired
    AccessBD accessBD;

    //FIXME
    String bindingName = "attack-attempt";

    @Test
    @DisplayName("Test consume attack attempt with ip subnet already in DB functionality")
    public void givenAttackAttemptWithSubnetAlreadyInDB_whenAddAttackAttempt_thenNothingAdded() throws Exception {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoAlreadyAdded();
        //when
        producer.send(new GenericMessage<>(attemptToSave), bindingName);
        Thread.sleep(100);
        //then
        AttackAttemptEntity obtainedAttackAttempt = accessBD
                .findByIpSubnetAndServiceName(DataUtils.getIpSubnetExists(), attemptToSave.services().get(0));
        assertThat(obtainedAttackAttempt).isNull();
    }

    @Test
    @DisplayName("Test consume attack attempt with few same service names functionality")
    public void givenAttackAttemptWithSameServices_whenAddAttackAttempt_thenOneServiceAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoSameServices();
        long attemptsCount = accessBD.getAttackAttemptsCount();
        //when
        producer.send(new GenericMessage<>(attemptToSave), bindingName);
        //then
        IpSubnetEntity obtainedIpSubnet = accessBD.findByIpSubnet(attemptToSave.subnet());
        long attemptNewCount = accessBD.getAttackAttemptsCount();
        assertThat(obtainedIpSubnet).isNotNull();
        assertThat(attemptNewCount).isEqualTo(attemptsCount + 1);
    }

    @Test
    @DisplayName("Test consume attack attempt with different service names functionality")
    public void givenAttackAttemptWithDifferentServices_whenAddAttackAttempt_thenAllServicesAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoDifferentServices();
        long attemptsCount = accessBD.getAttackAttemptsCount();
        //when
        producer.send(new GenericMessage<>(attemptToSave), bindingName);
        //then
        IpSubnetEntity obtainedIpSubnet = accessBD.findByIpSubnet(attemptToSave.subnet());
        long attemptNewCount = accessBD.getAttackAttemptsCount();
        assertThat(obtainedIpSubnet).isNotNull();
        assertThat(attemptNewCount).isEqualTo(attemptsCount + attemptToSave.services().size());
    }
}

package com.access.repo;

import com.access.model.AttackAttemptEntity;
import com.access.service.BlockingListPopulatorService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@ComponentScan(basePackages = {"com.access.repo"})
@Sql(scripts = "classpath:test_data.sql")
public class AccessPostgreSQLDBTests {
    @Autowired
    AttackAttemptRepo attemptRepo;
    @Autowired
    IpSubnetRepo ipSubnetRepo;
    @Autowired
    AccessBD accessDB;

    @MockBean
    BlockingListPopulatorService blockingListPopulatorService;

    @Test
    @DisplayName("Test find attack attempt by ip subnet and service name functionality")
    public void givenIpSubnetAndServiceName_whenFindAttackAttempt_thenAttackAttemptReturned() {
        //given
        AttackAttemptEntity attemptToBeReturned = DataUtils.getAttackAttemptPersisted();
        //when
        AttackAttemptEntity obtainedAttempt = accessDB
                .findByIpSubnetAndServiceName(attemptToBeReturned.getIpSubnet(), attemptToBeReturned.getServiceName());
        //then
        assertThat(obtainedAttempt.getId()).isEqualTo(attemptToBeReturned.getId());
        assertThat(obtainedAttempt.getServiceName()).isEqualTo(attemptToBeReturned.getServiceName());
        assertThat(obtainedAttempt.getTimestamp()).isEqualTo(attemptToBeReturned.getTimestamp());
        assertThat(obtainedAttempt.getIpSubnet().getIpSubnet()).isEqualTo(attemptToBeReturned.getIpSubnet().getIpSubnet());
    }

    @Test
    @DisplayName("Test find attack attempt by incorrect ip subnet and service name functionality")
    public void givenIncorrectIpSubnetAndServiceName_whenFindAttackAttempt_thenNullReturned() {
        //given
        AttackAttemptEntity attemptToBeReturned = DataUtils.getAttackAttemptIpNotExists();
        //when
        AttackAttemptEntity obtainedAttempt = accessDB
                .findByIpSubnetAndServiceName(attemptToBeReturned.getIpSubnet(), attemptToBeReturned.getServiceName());
        //then
        assertThat(obtainedAttempt).isNull();
    }

    @Test
    @DisplayName("Test find attack attempt by ip subnet and incorrect service name functionality")
    public void givenIpSubnetAndIncorrectServiceName_whenFindAttackAttempt_thenNullReturned() {
        //given
        AttackAttemptEntity attemptToBeReturned = DataUtils.getAttackAttemptServiceNotExists();
        //when
        AttackAttemptEntity obtainedAttempt = accessDB
                .findByIpSubnetAndServiceName(attemptToBeReturned.getIpSubnet(), attemptToBeReturned.getServiceName());
        //then
        assertThat(obtainedAttempt).isNull();
    }
}

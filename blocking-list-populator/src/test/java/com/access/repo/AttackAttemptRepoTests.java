package com.access.repo;

import com.access.model.AttackAttemptEntity;
import com.access.model.IpSubnetEntity;
import com.access.service.BlockingListPopulatorService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:test_data.sql")
public class AttackAttemptRepoTests {

    @Autowired
    AttackAttemptRepo attemptRepo;

    @MockBean
    BlockingListPopulatorService blockingListPopulatorService;

    @BeforeEach
    public void setUp() {
        attemptRepo.deleteAll();
    }

    @Test
    @DisplayName("Test save attack attempt functionality")
    public void givenAttackAttemptObject_whenSave_thenAttackAttemptIsCreated() {
        //FIXME
        //given
        AttackAttemptEntity attemptToSave = DataUtils.getAttackAttemptTransient();
        //when
        AttackAttemptEntity savedAttempt = attemptRepo.save(attemptToSave);
        //then
        assertThat(savedAttempt).isNotNull();
        assertThat(savedAttempt).isEqualTo(DataUtils.getAttackAttemptPersisted());
    }







    /*


        @Override
    public AttackAttemptEntity findByIpSubnetAndServiceName(IpSubnetEntity ipSubnet, String serviceName) {
        return attemptRepo.findByIpSubnetAndServiceName(ipSubnet, serviceName);
    }

    @Override
    public AttackAttemptEntity save(AttackAttemptEntity entity) {
        return attemptRepo.save(entity);
    }

     */
}

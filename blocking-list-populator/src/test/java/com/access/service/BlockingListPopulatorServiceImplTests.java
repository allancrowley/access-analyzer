package com.access.service;

import com.access.dto.AttackAttemptDto;
import com.access.model.*;
import com.access.repo.AccessPostgreSQLDB;
import com.access.util.DataUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockingListPopulatorServiceImplTests {
    @Mock
    AccessPostgreSQLDB accessBD;

    @InjectMocks
    BlockingListPopulatorServiceImpl blockingListPopulatorService;


    @Test
    @DisplayName("Test add attack attempt with ip subnet already in DB functionality")
    public void givenAttackAttemptWithSubnetAlreadyInDB_whenAddAttackAttempt_thenNothingAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoAlreadyAdded();
        BDDMockito.given(accessBD.findByIpSubnet(anyString())).willReturn(DataUtils.getIpSubnetExists());
        //when
        blockingListPopulatorService.addAttackAttemptDto(attemptToSave);
        //then
        verify(accessBD, times(0)).save(any(IpSubnetEntity.class));
        verify(accessBD, times(0)).save(any(AttackAttemptEntity.class));
    }

    @Test
    @DisplayName("Test add attack attempt with few same service names functionality")
    public void givenAttackAttemptWithSameServices_whenAddAttackAttempt_thenOneServiceAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoDifferentServices();
        BDDMockito.given(accessBD.findByIpSubnet(anyString())).willReturn(null);
        BDDMockito.given(accessBD.findByIpSubnetAndServiceName(DataUtils.getIpSubnetNotExists(), attemptToSave.services().get(0))).willReturn(DataUtils.getAttackAttemptPersisted());
        //when
        blockingListPopulatorService.addAttackAttemptDto(attemptToSave);
        //then
        verify(accessBD, times(1)).save(any(IpSubnetEntity.class));
        verify(accessBD, times(1)).save(any(AttackAttemptEntity.class));
    }

    @Test
    @DisplayName("Test add attack attempt with different service names functionality")
    public void givenAttackAttemptWithDifferentServices_whenAddAttackAttempt_thenAllServicesAdded() {
        //given
        AttackAttemptDto attemptToSave = DataUtils.getAttackAttemptDtoDifferentServices();
        BDDMockito.given(accessBD.findByIpSubnet(anyString())).willReturn(null);
        //when
        blockingListPopulatorService.addAttackAttemptDto(attemptToSave);
        //then
        verify(accessBD, times(1)).save(any(IpSubnetEntity.class));
        verify(accessBD, times(attemptToSave.services().size())).save(any(AttackAttemptEntity.class));
    }


}

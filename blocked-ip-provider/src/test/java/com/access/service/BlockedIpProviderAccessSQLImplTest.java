package com.access.service;

import com.access.repo.AccessSQLImpl;
import com.access.repo.BlockedSubnetRepo;
import com.access.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BlockedIpProviderAccessSQLImplTest {
    @Mock
    private BlockedSubnetRepo blockedSubnetRepo;
    @InjectMocks
    AccessSQLImpl accessSQLImpl;

    @Test
    @DisplayName("Test get blocked ips functionality")
    public void givenDataBaseWhenGetIpsThenListIpsReturns() {
        //given
        List<String> expectedIps = DataUtils.getSubnetList();
        BDDMockito.given(blockedSubnetRepo.findAllIpSubnetStringsBy()).
                willReturn(expectedIps);
        //when
        List<String> ips = accessSQLImpl.getBlockedList();
        //then
        assertThat(ips).isNotNull();
        assertEquals(expectedIps, ips);
    }
}

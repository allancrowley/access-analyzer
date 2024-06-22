package com.access.repo;

import com.access.model.IpSubnetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import util.DataUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan("com.access.repo")
public class BlockedIpProviderRepositoryTest {
    @Autowired
    private AccessDb accessDb;

    @BeforeEach
    public void setUp() {
        accessDb.deleteAll();
    }

    @Test
    @DisplayName("Test get blocked ip functionality using H2")
    public void givenDataBaseWhenGetIpsThenListIpsReturns() {
        //given
        List<String> expectedIps = DataUtils.getSubnetList();
        List<String> ips = accessDb.getBlockedList();
        assertTrue(ips.isEmpty());
        List<IpSubnetEntity> ipSubnetEntities = DataUtils.getIpSubnetEntitiesList();
        accessDb.saveAll(ipSubnetEntities);
        //when
        ips = accessDb.getBlockedList();
        //then
        assertThat(ips).isNotNull();
        assertEquals(ips, expectedIps);
    }
}
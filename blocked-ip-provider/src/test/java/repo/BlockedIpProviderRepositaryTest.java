package com.access.repo;

import com.access.model.IpSubnetEntity;
import com.access.repo.AccessDb;
import com.access.repo.AccessSQLImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import util.DataUtils;

import javax.xml.crypto.Data;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan("com.access.repo")
public class BlockedIpProviderRepositaryTest {
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
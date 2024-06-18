package com.access.repo;

import com.access.model.IpSubnetEntity;
import com.access.service.BlockingListPopulatorService;
import com.access.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class IpSubnetRepoTests {

    @Autowired
    private IpSubnetRepo ipSubnetRepo;

    @MockBean
    BlockingListPopulatorService blockingListPopulatorService;

    @BeforeEach
    public void setUp() {
        ipSubnetRepo.deleteAll();
    }

    @Test
    @DisplayName("Test find ip subnet by id functionality")
    public void givenId_whenFindById_thenIpSubnetIsReturned() {
        //given
        IpSubnetEntity subnetToSave = DataUtils.getIpSubnetExists();
        ipSubnetRepo.save(subnetToSave);
        //when
        IpSubnetEntity obtainedSubnet = ipSubnetRepo.findById(subnetToSave.getIpSubnet()).orElse(null);
        //then
        assertThat(obtainedSubnet).isNotNull();
        assertThat(obtainedSubnet.getIpSubnet()).isEqualTo(subnetToSave.getIpSubnet());
    }

    @Test
    @DisplayName("Test subnet not found functionality")
    public void givenSubnetIsNotSaved_whenFindById_thenSubnetIsNotFound() {
        //given
        IpSubnetEntity subnetToFind = DataUtils.getIpSubnetExists();
        //when
        IpSubnetEntity obtainedSubnet = ipSubnetRepo.findById(subnetToFind.getIpSubnet()).orElse(null);
        //then
        assertThat(obtainedSubnet).isNull();
    }

    @Test
    @DisplayName("Test save subnet functionality")
    public void givenSubnetObject_whenSave_thenSubnetIsCreated() {
        //given
        IpSubnetEntity subnetToSave = DataUtils.getIpSubnetExists();
        //when
        IpSubnetEntity savedSubnet = ipSubnetRepo.save(subnetToSave);
        //then
        assertThat(savedSubnet).isNotNull();
        assertThat(savedSubnet.getIpSubnet()).isNotNull();
    }

}

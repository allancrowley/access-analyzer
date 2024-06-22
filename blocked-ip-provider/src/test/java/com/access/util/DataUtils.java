package com.access.util;

import com.access.model.IpSubnetEntity;

import java.util.Arrays;
import java.util.List;

public class DataUtils {
    public static List<String> getSubnetList() {
        return Arrays.asList("111.111.111", "222.222.222", "333.333.333", "444.444.444", "555.555.555");
    }

    public static List<IpSubnetEntity> getIpSubnetEntitiesList() {
        return Arrays.asList(
                new IpSubnetEntity("111.111.111"),
                new IpSubnetEntity("222.222.222"),
                new IpSubnetEntity("333.333.333"),
                new IpSubnetEntity("444.444.444"),
                new IpSubnetEntity("555.555.555")
        );
    }
}

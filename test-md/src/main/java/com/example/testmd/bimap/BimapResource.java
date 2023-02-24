package com.example.testmd.bimap;

import com.example.testmd.common.bo.Student;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author PeiDong Yan
 * @date 2023/02/14 9:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BimapResource {

    @Test
    public void t1() {
        Map<Integer, String> map1 = Maps.newHashMap();
        map1.put(1, "11");
        map1.put(2, "11");
        map1.put(3, "33");
        map1.put(4, "44");
        HashBiMap map=HashBiMap.create(map1);
        BiMap inverse = map.inverse();
        Object o = inverse.get("33");
        Object o2 = inverse.get("11");
    }

}

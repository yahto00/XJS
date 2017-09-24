package com.pipi.test;

import com.pipi.util.DSUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yahto on 12/05/2017.
 */
public class DSUtilTest {
    @Test
    public void parseIntegerArrTest() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(DSUtil.parseIntegerArr(integers));
    }

    @Test
    public void parseObjectListTest() {
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(DSUtil.parseObjectList(list));
    }
}

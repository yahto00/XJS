package com.pipi.test;

import com.pipi.util.DSUtil;
import org.junit.Test;

/**
 * Created by yahto on 12/05/2017.
 */
public class DSUtilTest {
    @Test
    public void parseIntegerArrTest(){
        Integer[] integers = {1,2,3,4,5,6,7};
        System.out.println(DSUtil.parseIntegerArr(integers));
    }
}

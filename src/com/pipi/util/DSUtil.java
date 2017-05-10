package com.pipi.util;

import java.util.List;

/**
 * Created by yantong on 08/04/2017.
 */
public class DSUtil {
    /**
     * 判断list2 是不是list1的子集 是返回true 否返回false
     * @param list1
     * @param list2
     * @return
     */
    private boolean isSubset(List<Integer> list1, List<Integer> list2){
        boolean isMatched = true;
        for(Integer num: list2){
            if(!list1.contains(num)){
                isMatched = false;
                break;
            }
        }
        return isMatched;
    }

}

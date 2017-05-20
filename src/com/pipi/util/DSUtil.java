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
    private static boolean isSubset(List<Integer> list1, List<Integer> list2){
        boolean isMatched = true;
        for(Integer num: list2){
            if(!list1.contains(num)){
                isMatched = false;
                break;
            }
        }
        return isMatched;
    }

    /**
     * 将Integer数组转为字符串
     * @param integers
     * @return
     */
    public static String parseIntegerArr(Integer[] integers){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < integers.length-1; i++) {
            stringBuilder.append(integers[i]+",");
        }
        stringBuilder.append(integers[integers.length-1]);
        return stringBuilder.toString();
    }

    /**
     * 将List<Object>转为String
     * @param list
     * @return
     */
    public static String parseObjectList(List<Object> list){
        StringBuilder stringBuilder = new StringBuilder();
        int length = list.size();
        for (int i = 0; i < length-1; i++) {
            stringBuilder.append(list.get(i).toString()+",");
        }
        stringBuilder.append(list.get(length -1).toString());
        return stringBuilder.toString();
    }



}

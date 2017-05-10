package com.pipi.util;

import com.pipi.common.constant.SystemConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 03/05/2017.
 */
public class PageUtil {
    /** 分页工具 返回map对象 map包含所需要的list 还有总的分页页数*/
    public static Map<String,Object> getPageAndList(Integer startPage, Integer pageSize, List<?> list){
        Map<String, Object> map = new HashMap<>();
        if(startPage == null || startPage < 1)
            startPage =1;
        if(pageSize == null || pageSize < 1)
            pageSize = SystemConstant.PAGE_SIZE;
        int pages = 0;
        if (list.size() == 0){
            pages = 1;
        }else {
            pages = (list.size()-1)/pageSize +1;
        }
        if(pages>1){
            list = list.subList((startPage-1)*pageSize, (startPage*pageSize>list.size())?list.size():startPage*pageSize);
        }
        map.put("pageNum", pages);
        map.put("list", list);
        return map;
    }
}

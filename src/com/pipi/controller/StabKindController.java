package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.StabKind;
import com.pipi.service.IStabKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yahto on 14/05/2017.
 */
@Controller
public class StabKindController extends BaseController{
    @Autowired
    private IStabKindService stabKindService;

    /**
     * 添加扎功能
     * @author yahto
     * @param stabKind
     * @return
     */
    @RequestMapping("stabKind_addStabKind.ajax")
    @ResponseBody
    public Map<String,Object> addStabKind(StabKind stabKind){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",false);
        try {
            stabKindService.addStabKind(stabKind);
            map.put("msg","操作成功");
            map.put("data",true);
        }catch (BusinessException e){
            map.put("msg",e.getMessage());
        }
        return map;
    }
}

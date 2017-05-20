package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.IStabKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 14/05/2017.
 */
@Controller
public class StabKindController extends BaseController {
    @Autowired
    private IStabKindService stabKindService;

    /**
     * 添加扎功能
     *
     * @param stabKind
     * @return
     * @author yahto
     */
    @RequestMapping("stabKind_addStabKind.ajax")
    @ResponseBody
    public Map<String, Object> addStabKind(StabKind stabKind) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            stabKindService.addStabKind(stabKind);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 批量删除扎功能
     *
     * @param ids
     * @return
     * @author yahto
     */
    @RequestMapping("stabKind_deleteStabKindByIds.ajax")
    @ResponseBody
    public Map<String, Object> deleteStabKindByIds(Integer[] ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            stabKindService.deleteStabKindByIds(ids);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 查询所有扎种类
     *
     * @return
     * @author  hbwj
     */
    @RequestMapping("stabKind_queryAllStabKind.ajax")
    @ResponseBody
     public Map<String,Object>  queryAllStabKind(){
        Map<String,Object> map  =  new HashMap<String,Object>();
        map.put("data",false);
        try {
            List<StabKind> list = stabKindService.queryAllStabKind();
            map.put("list",list);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
     }

    /**
     * 修改扎种类
     *
     * @param stabKind
     * @return
     */
    @RequestMapping("stabKind_updateStabKind.ajax")
    @ResponseBody
     public  Map<String,Object> updateStabKind(StabKind stabKind){

        Map<String,Object> map  =  new HashMap<String,Object>();
        map.put("data",false);
        try {
             stabKindService.updateStabKind(stabKind);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
     }
}

package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Kind;
import com.pipi.service.iservice.IKindService;
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
public class KindController extends BaseController {
    @Autowired
    private IKindService kindService;

    /**
     * 添加种类功能
     *
     * @param kind
     * @return
     * @author yahto
     */
    @RequestMapping("kind_addKind.ajax")
    @ResponseBody
    public Map<String, Object> addKind(Kind kind) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            kindService.add(kind);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 根据id查询种类
     *
     * @param id
     * @return
     * @author yahto
     */
    @RequestMapping("kind_queryKindById.ajax")
    @ResponseBody
    public Map<String, Object> queryKindById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            if (id == null)
                throw new BusinessException("未指定种类");
            Kind kind = (Kind) kindService.queryObjectByID(Kind.class, id);
            map.put("msg", "操作成功");
            map.put("data", true);
            map.put("kind", kind);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 编辑种类功能
     *
     * @param kind
     * @return
     * @author yahto
     */
    @RequestMapping("kind_editKind.ajax")
    @ResponseBody
    public Map<String, Object> editKind(Kind kind) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            if (kind == null)
                throw new BusinessException("未指定种类");
            Kind existKind = (Kind) kindService.queryObjectByID(Kind.class, kind.getId());
            if (existKind == null)
                throw new BusinessException("要编辑的种类不存在");
            existKind.setName(kind.getName());
            existKind.setNum(kind.getNum());
            kindService.update(existKind);
            map.put("msg", "操作成功");
            map.put("data", true);
            map.put("kind", kind);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 批量删除种类
     *
     * @param ids
     * @return
     * @author yahto
     */
    @RequestMapping("kind_deleteKindByIds.ajax")
    @ResponseBody
    public Map<String, Object> deleteKindByIds(Integer[] ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            kindService.deleteKindByIds(ids);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

}

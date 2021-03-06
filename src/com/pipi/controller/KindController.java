package com.pipi.controller;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Kind;
import com.pipi.service.iservice.IKindService;
import com.pipi.util.ObjectUtil;
import com.pipi.vo.Page;
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
            String[] params = {"name", "num"};
            if (null == kind || ObjectUtil.objectIsEmpty(kind, params)) {
                throw new BusinessException("未填写种类的完整信息");
            } else {
                try {
                    kindService.add(kind);
                } catch (Exception e) {
                    map.put("msg", e.getMessage());
                    return map;
                }
            }
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
            String[] params = {"name", "num"};
            if (null == kind || ObjectUtil.objectIsEmpty(kind, params))
                throw new BusinessException("未填写种类的完整信息");
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

    /**
     * 查询所有种类信息功能
     *
     * @return
     * @author yahto
     */
    @RequestMapping("kind_queryAllKind.ajax")
    @ResponseBody
    public Map<String, Object> queryAllKind(Integer startPage, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            if (startPage == null) {
                startPage = 1;
            }
            if (pageSize == null) {
                pageSize = SystemConstant.PAGE_SIZE;
            }
            Page page = new Page();
            page.setStartPage(startPage);
            page.setPageSize(pageSize);
            List<Kind> list = kindService.queryAllKind(page);
            map.put("page", page);
            map.put("list", list);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

}

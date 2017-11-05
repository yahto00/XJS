package com.pipi.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pipi.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pipi.controller.BaseController;
import com.pipi.entity.admin.Role;
import com.pipi.service.iservice.adminIService.IRoleService;


/**
 * Created by yahto on 07/05/2017.
 */
@Controller
public class RoleController extends BaseController {

    @Autowired
    IRoleService roleService;

    /**
     * 查询所有角色
     *
     * @return
     * @author yahto
     */
    @RequestMapping("role_queryAllRole.ajax")
    @ResponseBody
    public Map<String, Object> queryAllRole() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            List<Role> list = (List<Role>) roleService.queryAll(Role.class);
            map.put("list", list);
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}

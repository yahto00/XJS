package com.pipi.controller;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Development;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.IDevelopmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 14/10/2017
 */
@Controller
public class DevelopmentController {
    @Autowired
    private IDevelopmentService developmentService;

    /**
     * 查询所有未出售的成品
     *
     * @param request
     * @return
     */
    @RequestMapping("development_queryAllDevelopment.ajax")
    @ResponseBody
    public Map<String, Object> queryAllDevelopment(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("data", false);
        try {
            if (request.getSession().getAttribute(SystemConstant.CURRENT_USER) == null) {
                throw new BusinessException("请先登录");
            }
            List<Development> list = developmentService.queryAllDevelopment((User) request.getSession().getAttribute(SystemConstant.CURRENT_USER));
            map.put("list", list);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}

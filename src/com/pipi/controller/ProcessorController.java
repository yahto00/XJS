package com.pipi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Development;
import com.pipi.entity.ProcessSlate;
import com.pipi.service.iservice.IDevelopmentService;
import com.pipi.service.iservice.IProcessorService;
import com.pipi.vo.Page;
import com.pipi.vo.ProcessSlateVo;
import com.pipi.vo.SlateDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 10/09/2017.
 */
@Controller
public class ProcessorController extends BaseController {
    @Autowired
    private IProcessorService processorService;
    @Autowired
    private IDevelopmentService developmentService;

    /**
     * 加工间返库
     *
     * @param data
     * @return
     * @author yahto
     */
    @RequestMapping("processor_backStorage.ajax")
    @ResponseBody
    public Map<String, Object> backStorage(@RequestBody String data) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("data", false);
        try {
            JSONObject jsonObject = JSON.parseObject(data);
            JSONArray array = jsonObject.getJSONArray("data");
            //得到长宽数据List
            List<SlateDataVo> voList = JSONArray.parseArray(array.toJSONString(), SlateDataVo.class);
            processorService.backStorage(jsonObject.getInteger("processSlateId"),
                    jsonObject.getInteger("stabKindId"), jsonObject.getString("description"), voList);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    //Integer processSlateId, Integer stabKindId, String description, float length, float width

    /**
     * 查询加工间所有板材
     *
     * @return
     * @author yahto
     */
    @RequestMapping("processor_queryProcessSlateByPage.ajax")
    @ResponseBody
    public Map<String, Object> queryProcessSlateByPage(Integer startPage, Integer pageSize, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("data", false);
        try {
            if (request.getSession().getAttribute(SystemConstant.CURRENT_USER) == null) {
                throw new BusinessException("未登录,请先登录!");
            }
            if (startPage == null) {
                startPage = 1;
            }
            if (pageSize == null) {
                pageSize = SystemConstant.PAGE_SIZE;
            }
            Page page = new Page();
            page.setStartPage(startPage);
            page.setPageSize(pageSize);
            List<ProcessSlate> processSlateList = (List<ProcessSlate>) processorService.queryProcessSlateByPage(getCurrentUser(request), page);
            List<ProcessSlateVo> list = new ArrayList<ProcessSlateVo>();
            for (ProcessSlate processSlate : processSlateList) {
                ProcessSlateVo processSlateVo = new ProcessSlateVo();
                processSlateVo.setStabKindId(processSlate.getStabKind().getId());
                processSlateVo.setSlateId(processSlate.getId());
                processSlateVo.setPrice(processSlate.getPrice());
                processSlateVo.setAcreage(processSlate.getAcreage());
                processSlateVo.setProNum(processSlate.getProNum());
                processSlateVo.setOperatorName(processSlate.getUser().getUserName());
                processSlateVo.setKindName(processSlate.getKind().getName());
                processSlateVo.setStabKindNum(processSlate.getStabKind().getNum());
                list.add(processSlateVo);
            }
            map.put("list", list);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 生成成品
     *
     * @param data
     * @param request
     * @return
     * @author yahto
     */
    @RequestMapping("processor_produceDevelopment.ajax")
    @ResponseBody
    public Map<String, Object> produceDevelopment(@RequestBody String data, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("data", false);
        try {
            JSONObject jsonObject = JSON.parseObject(data);
            Integer processSlateId = jsonObject.getInteger("processSlateId");
            JSONArray developmentArr = jsonObject.getJSONArray("development");
            List<Development> list = JSONArray.parseArray(developmentArr.toJSONString(), Development.class);
            developmentService.produceDevelopment(processSlateId, list, request);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}

package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.ProcessSlate;
import com.pipi.service.iservice.IProcessorService;
import com.pipi.vo.ProcessSlateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 10/09/2017.
 */
@Controller
public class ProcessorController {
    @Autowired
    private IProcessorService processorService;

    /**
     * 返库
     *
     * @param processSlateId
     * @param stabKindId
     * @param description
     * @param length
     * @param width
     * @return
     * @author yahto
     */
    @RequestMapping("processor_backStorage.ajax")
    @ResponseBody
    public Map<String, Object> backStorage(Integer processSlateId, Integer stabKindId, String description, float length, float width) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("data", false);
        try {
            processorService.backStorage(processSlateId, stabKindId, description, length, width);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 查询加工间所有板材
     *
     * @return
     * @author yahto
     */
    @RequestMapping("processor_queryAllProcessSlate.ajax")
    @ResponseBody
    public Map<String, Object> queryAllProcessSlate() {
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("data", false);
        try {
            List<ProcessSlate> processSlateList = (List<ProcessSlate>) processorService.queryAllProcessSlate();
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
}

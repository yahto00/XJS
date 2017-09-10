package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.service.iservice.IProcessorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yahto on 10/09/2017.
 */
@Controller
public class ProcessorController {
    private IProcessorService processorService;

    @RequestMapping("processor_backStorage.ajax")
    @ResponseBody
    public Map<String, Object> backStorage(Integer processSlateId, Integer stabKindId, String description, float length, float width) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("data", false);
        try {
            processorService.backStorage(processSlateId, stabKindId, description, length, width);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}

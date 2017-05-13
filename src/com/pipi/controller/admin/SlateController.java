package com.pipi.controller.admin;

import com.pipi.common.exception.BusinessException;
import com.pipi.controller.BaseController;
import com.pipi.entity.Slate;
import com.pipi.service.ISlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yahto on 13/05/2017.
 */
@Controller
public class SlateController extends BaseController{
    @Autowired
    private ISlateService slateService;

    /**
     * 增加板材功能
     * @author yahto
     * @param slate
     * @param kindId
     * @param stabKindId
     * @return
     */
    @RequestMapping("slate_addSlate.ajax")
    @ResponseBody
    public Map<String,Object> addSlate(Slate slate,Integer kindId,Integer stabKindId){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",false);
        try {
            slateService.addSlate(slate,kindId,stabKindId);
            map.put("msg","操作成功");
            map.put("data",true);
        }catch (BusinessException e){
            map.put("msg",e.getMessage());
        }
        return map;
    }
}

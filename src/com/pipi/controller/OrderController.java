package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Order;
import com.pipi.service.iservice.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 07/05/2017.
 */
@Controller
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;

    /**
     * @author yahto
     * @return
     */
    @RequestMapping("order_getAllOrder.ajax")
    @ResponseBody
    public Map<String,Object> userLogin(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",false);
        try {
            List<Order> list = orderService.getAllOrder();
            map.put("data",true);
            map.put("msg","操作成功");
        }catch (BusinessException e){
            map.put("msg",e.getMessage());
        }
        return map;
    }


}
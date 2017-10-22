package com.pipi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Order;
import com.pipi.service.iservice.IOrderService;
import com.pipi.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 07/05/2017.
 */
@Controller
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    /**
     * 查询所有订单
     *
     * @return
     * @author yahto
     */
    @RequestMapping("order_queryAllOrder.ajax")
    @ResponseBody
    public Map<String, Object> queryAllOrder() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            List<Order> list = orderService.getAllOrder();
            map.put("list", list);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 添加订单
     *
     * @param data
     * @param request
     * @return
     * @author yahto
     */
    @RequestMapping("order_addOrder.ajax")
    @ResponseBody
    public Map<String, Object> addOrder(@RequestBody String data, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            JSONObject jsonObject = JSON.parseObject(data);
            Integer customerId = jsonObject.getInteger("customerId");
            String orderNum = jsonObject.getString("orderNum");
            List<OrderVo> list = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), OrderVo.class);
            orderService.addOrder(customerId, list, orderNum, request);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
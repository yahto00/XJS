package com.pipi.controller;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Customer;
import com.pipi.service.ICustomerService;
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
public class CustomerController extends BaseController{

    @Autowired
    private ICustomerService customerService;

    /**
     * 查询所有客户
     * @author yahto
     * @return
     */
    @RequestMapping("customer_queryAllCustomer.ajax")
    @ResponseBody
    public Map<String,Object> queryAllCustomer(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",false);
        try {
            List<Customer> list = customerService.getAllCustomer();
            map.put("list",list);
            map.put("data",true);
            map.put("msg","操作成功");
        }catch (BusinessException e){
            map.put("msg","操作失败");
        }
        return map;
    }

    /**
     * 根据id查询客户
     * @author yahto
     * @param id
     * @return
     */
    @RequestMapping("customer_queryCustomerById.ajax")
    @ResponseBody
    public Map<String,Object> queryCustomerById(Integer id){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",false);
        try {
            Customer customer = customerService.getCustomerById(id);
            map.put("customer",customer);
            map.put("data",true);
            map.put("msg","操作成功");
        }catch (BusinessException e){
            map.put("msg","操作失败");
        }
        return map;
    }

    /**
     * 按条件查询用户
     * @author yahto
     * @param customerName
     * @param phone
     * @return
     */
    @RequestMapping("customer_queryCustomerByCondition.ajax")
    @ResponseBody
    public Map<String,Object> queryCustomerByCondition(String customerName,String phone){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",false);
        try {
            List<Customer> list = customerService.queryCustomerByCondition(customerName,phone);
            map.put("list",list);
            map.put("data",true);
            map.put("msg","操作成功");
        }catch (BusinessException e){
            map.put("msg",e.getMessage());
        }
        return map;
    }

}

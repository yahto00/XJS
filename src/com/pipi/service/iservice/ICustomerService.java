package com.pipi.service.iservice;

import com.pipi.entity.Customer;

import java.util.List;

/**
 * Created by yahto on 07/05/2017.
 */
public interface ICustomerService extends IBaseService{
    /** 查询所有客户*/
    List<Customer> getAllCustomer();
    /** 根据id查询客户*/
    Customer getCustomerById(Integer id);
    /** 模糊查询客户*/
    List<Customer> queryCustomerByCondition(String customerName, String phone);
    /** 批量删除客户*/
    void deleteCustomersByIds(Integer[] ids);
}

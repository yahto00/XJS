package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yahto on 07/05/2017.
 */
@Service
public class CustomerService extends BaseService implements ICustomerService{
    @Override
    public List<Customer> getAllCustomer() {
        return baseDao.getAllObjects(Customer.class);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return (Customer) baseDao.getObjectByID(Customer.class,id);
    }

    @Override
    public List<Customer> queryCustomerByCondition(String customerName, String phone) {
        if (customerName == null && phone == null ){
            throw new BusinessException("输入条件为空");
        }
        StringBuilder hql =  new StringBuilder("from Customer  c where 1=1 and c.isDelete=0");
        if (customerName != null && customerName.length() !=0){
            hql.append(" and name like '%" + customerName + "%'");
        }
        if (phone != null && phone.length()!=0){
            hql.append(" and phone like '%" + phone + "%'");
        }
        return (List<Customer>) queryObjectList(hql.toString());
    }
}

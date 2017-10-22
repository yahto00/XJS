package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.common.aop.MyLog;
import com.pipi.entity.Customer;
import com.pipi.service.iservice.ICustomerService;
import com.pipi.util.DSUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yahto on 07/05/2017.
 */
@Service
public class CustomerService extends BaseService implements ICustomerService {
    @Override
    public List<Customer> getAllCustomer() {
        return (List<Customer>) queryAll(Customer.class);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return (Customer) baseDao.getObjectByID(Customer.class, id);
    }

    @Override
    public List<Customer> queryCustomerByCondition(String customerName, String phone) {
        if (customerName == null && phone == null) {
            throw new BusinessException("输入条件为空");
        }
        StringBuilder hql = new StringBuilder("from Customer  c where 1=1 and c.isDelete=0");
        if (customerName != null && customerName.length() != 0) {
            hql.append(" and name like '%" + customerName + "%'");
        }
        if (phone != null && phone.length() != 0) {
            hql.append(" and phone like '%" + phone + "%'");
        }
        return (List<Customer>) queryObjectList(hql.toString());
    }

    @Override
    @MyLog(operationName = "批量删除客户", operationType = "delete")
    public void deleteCustomersByIds(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("未指定要删除的客户");
        }
        delete(Customer.class, DSUtil.parseIntegerArr(ids));
    }
}

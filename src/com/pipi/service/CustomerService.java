package com.pipi.service;

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
}

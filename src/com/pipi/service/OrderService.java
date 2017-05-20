package com.pipi.service;

import com.pipi.entity.Order;
import com.pipi.service.iservice.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

    /**
     * Created by yahto on 07/05/2017.
     */
@Service
public class OrderService extends BaseService implements IOrderService {
    @Override
    public List<Order> getAllOrder() {
        return baseDao.getAllObjects(Order.class);
    }
}

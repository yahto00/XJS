package com.pipi.service.iservice;

import com.pipi.entity.Order;

import java.util.List;

/**
 * Created by yahto on 07/05/2017.
 */
public interface IOrderService extends IBaseService {
    /**
     * 查询所有订单
     */
    List<Order> getAllOrder();
}

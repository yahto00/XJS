package com.pipi.service.iservice;

import com.pipi.entity.Order;
import com.pipi.vo.OrderVo;
import com.pipi.vo.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yahto on 07/05/2017.
 */
public interface IOrderService extends IBaseService {
    /**
     * 查询所有订单
     */
    List<Order> getAllOrder(Page page);

    /**
     * 添加订单
     *
     * @param customerId
     * @param list
     * @param orderNum
     * @param request
     */
    void addOrder(Integer customerId, List<OrderVo> list, String orderNum, HttpServletRequest request);
}

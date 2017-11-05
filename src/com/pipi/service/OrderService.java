package com.pipi.service;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Customer;
import com.pipi.entity.Development;
import com.pipi.entity.Order;
import com.pipi.entity.OrderItem;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.ICustomerService;
import com.pipi.service.iservice.IOrderService;
import com.pipi.vo.OrderVo;
import com.pipi.vo.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yahto on 07/05/2017.
 */
@Service
public class OrderService extends BaseService implements IOrderService {

    @Override
    public List<Order> getAllOrder(Page page) {
        if (page.getTotalCount() == null || page.getTotalCount() == 0) {
            Integer totalCount = baseDao.getObjectCount(Order.class);
            if (totalCount == null || totalCount == 0) {
                page.setTotalCount(0);
            } else {
                page.setTotalCount(totalCount);
            }
        }
        return (List<Order>) baseDao.getAllObjectByPage(Order.class, page);
    }

    @Override
    public void addOrder(Integer customerId, List<OrderVo> list, String orderNum, HttpServletRequest request) {
        if (customerId == null || StringUtils.isEmpty(orderNum)) {
            throw new BusinessException("请填写完整信息");
        }
        User currentUser = (User) request.getSession().getAttribute(SystemConstant.CURRENT_USER);
        if (currentUser == null) {
            throw new BusinessException("请先登录");
        }
        if (list.size() == 0) {
            throw new BusinessException("请选择下单的成品");
        }
        Order order = new Order();
        Customer customer = (Customer) queryObjectByID(Customer.class, customerId);
        if (customer == null) {
            throw new BusinessException("未指定客户");
        }
        order.setCustomer(customer);
        order.setOrderNum(orderNum);
        order.setTotalCount(list.size());
        order.setUser(currentUser);
        save(order);
        Double totalPrice = 0d;
        for (OrderVo orderVo : list) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCount(orderVo.getCount());
            orderItem.setOrder(order);
            orderItem.setDevelopmentId(orderVo.getDevelopmentId());
            Development development = (Development) baseDao.getObjectByID(Development.class, orderVo.getDevelopmentId());
            if (development.getCount() < orderVo.getCount()) {
                throw new BusinessException("成品数量不够");
            }
            development.setCount(development.getCount() - orderVo.getCount());
            if (development.getCount() == 0) {
                development.setStatus(1);
            }
            orderItem.setPrice(development.getPrice() * orderVo.getCount());
            orderItem.setSlateName(development.getSlateName());
            update(development);
            save(orderItem);
            order.getOrderItems().add(orderItem);
            totalPrice += orderItem.getPrice();
        }
        order.setPrice(totalPrice.floatValue());
        update(order);
        customer.setLast_order(order);
        update(customer);
    }
}

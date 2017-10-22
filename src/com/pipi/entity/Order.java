package com.pipi.entity;

import com.pipi.common.constant.SystemConstant;
import com.pipi.entity.admin.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "T_ORDER")
public class Order extends BaseEntity {
    private static final long serialVersionUID = 5926553015764565L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_ORDER_ID")
    private Integer id;

    /**
     * 订单编号
     */
    @Column(name = "ORDER_NUM", length = 100)
    private String orderNum;

    /**
     * 订单条目总数量
     */
    @Column(name = "ORDER_TOTAL_COUNT")
    private Integer totalCount;

    /**
     * 订单金额
     */
    @Column(name = "ORDER_TOTAL_PRICE")
    private float price;

    /**
     * 客户
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CUSTOMER_ID")
    private Customer customer;
    /**
     * 生成订单时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "createTime")
    @DateTimeFormat(pattern = SystemConstant.DATE_PATTEN)
    private Date createTime = new Date();

    /**
     * 订单条目关联信息
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<OrderItem>();

    /**
     * 订单操作人
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_OPERATE_USER_ID")
    private User user;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

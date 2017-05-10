package com.pipi.entity;

import javax.persistence.*;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "T_ORDER")
public class Order extends BaseEntity{
    private static final long serialVersionUID = 5926553015764565L;
    /** 主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "PK_ORDER_ID")
    private Integer id;

    /** 订单编号 */
    @Column(name = "ORDER_NUM", length = 100)
    private String orderNum;

    /** 订单条目总数量*/
    @Column(name = "ORDER_TOTAL_COUNT")
    private Integer totalCount;

    /** 订单金额*/
    @Column(name = "ORDER_TOTAL_PRICE")
    private float price;

    /** 关联客户信息*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CUS",foreignKey = @ForeignKey(name = "FK_CUS"))
    private Customer customer;


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
}

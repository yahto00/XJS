package com.pipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipi.entity.admin.User;

import javax.persistence.*;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "T_DEVELOPMENT")
public class Development extends BaseEntity {
    private static final long serialVersionUID = 59265530157645975L;

    /**
     * 成品主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_DEVELOPMENT_ID")
    private Integer id;

    /**
     * 成品名
     */
    @Column(name = "SLATE_NAME", length = 100)
    private String slateName;

    /**
     * 成品单价
     */
    @Column(name = "SLATE_PRICE")
    private Double price;

    /**
     * 成品的长度
     */
    @Column(name = "SLATE_LENGTH")
    private float length;

    /**
     * 成品的宽度
     */
    @Column(name = "SLATE_HEIGHT")
    private float height;

    /**
     * 石材加工单操作人
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_OPERATE_USER_ID")
    private User user;

    /**
     * 成品的来源
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_PROCESS_SLATE_ID")
    @JsonIgnore
    private ProcessSlate processSlate;

    /**
     * 关联所属订单条目
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORDERITEM_ID")
    private OrderItem orderItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlateName() {
        return slateName;
    }

    public void setSlateName(String slateName) {
        this.slateName = slateName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public ProcessSlate getProcessSlate() {
        return processSlate;
    }

    public void setProcessSlate(ProcessSlate processSlate) {
        this.processSlate = processSlate;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
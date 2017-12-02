package com.pipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "t_order_item")
public class OrderItem extends BaseEntity {
    private static final long serialVersionUID = -19265530157645975L;
    /**
     * 条目主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_order_item_id")
    private Integer id;

    /**
     * 条目名
     */
    @Column(name = "order_item_name", length = 100)
    private String slateName;

    /**
     * 条目单价
     */
    @Column(name = "order_item_price")
    private Double price;

    /**
     * 条目数量
     */
    @Column(name = "order_item_count")
    private Integer count;

    /**
     * 订单条目关联的成品
     */
    @JsonIgnore
    private Integer developmentId;

    /**
     * 条目所属订单
     */
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORDER_ID")
    private Order order;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDevelopmentId() {
        return developmentId;
    }

    public void setDevelopmentId(Integer developmentId) {
        this.developmentId = developmentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

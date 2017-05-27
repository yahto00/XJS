package com.pipi.entity;

import javax.persistence.*;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "T_ORDER_ITEM")
public class OrderItem extends BaseEntity{
    private static final long serialVersionUID = -19265530157645975L;
    /** 条目主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "PK_ORDER_ITEM_ID")
    private Integer id;

    /** 条目名 */
    @Column(name = "ORDER_ITEM_NAME", length = 100)
    private String slateName;

    /** 条目单价*/
    @Column(name = "ORDER_ITEM_PRICE")
    private Double price;

    /** 条目数量*/
    @Column(name = "ORDER_ITEM_COUNT")
    private Integer count;

    /** 条目所属订单*/

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

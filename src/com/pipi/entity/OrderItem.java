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
    /** 关联所属的种类*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ORDER",foreignKey = @ForeignKey(name = "FK_ORDER"))
    private Order order;

}

package com.pipi.entity;

import javax.persistence.*;

/**
 * Created by yahto on 07/05/2017.
 */
@Entity
@Table(name = "T_CUSTOMER")
public class Customer extends BaseEntity{

    private static final long serialVersionUID = 59265530157645L;

    /** 主键*/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "PK_CUS_ID")
    private Integer id;

    /** 姓名*/
    @Column(name = "CUS_NAME")
    private String name;

    /** 联系方式*/
    @Column(name = "CUS_PHONE")
    private String phone;

    /** 最近的订单*/
    @Column(name = "CUS_LAST_ORDER_ID")
    private Integer last_order_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLast_order_id() {
        return last_order_id;
    }

    public void setLast_order_id(Integer last_order_id) {
        this.last_order_id = last_order_id;
    }
}

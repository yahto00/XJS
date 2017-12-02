package com.pipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by yahto on 07/05/2017.
 */
@Entity
@Table(name = "t_customer")
public class Customer extends BaseEntity {

    private static final long serialVersionUID = 59265530157645L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_cus_id")
    private Integer id;

    /**
     * 姓名
     */
    @Column(name = "cus_name")
    private String name;

    /**
     * 联系方式
     */
    @Column(name = "cus_phone")
    private String phone;

    /**
     * 最近的订单
     */
    @Column(name = "cus_last_order_id")
    @JsonIgnore
    private Order last_order;

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

    public Order getLast_order() {
        return last_order;
    }

    public void setLast_order(Order last_order) {
        this.last_order = last_order;
    }
}

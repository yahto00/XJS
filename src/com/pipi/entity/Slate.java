package com.pipi.entity;

import javax.persistence.*;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "T_SLATE")
public class Slate extends BaseEntity{

    private static final long serialVersionUID = 5926553015764597895L;

    /** 石材主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "PK_SLATE_ID")
    private Integer id;

    /** 石材名 */
    @Column(name = "SLATE_NAME", length = 100)
    private String slateName;

    /** 石材单价*/
    @Column(name = "SLATE_PRICE")
    private Double price;

    /** 石材的长度*/
    @Column(name = "SLATE_LENGTH")
    private float length;

    /** 石材的宽度*/
    @Column(name = "SLATE_HEIGHT")
    private float height;

    /** 关联所属的扎*/
    @Column(name = "FK_STAB_KIND_ID")
    private Integer stabKindId;

    /** 关联所属的种类*/
    @Column(name = "FK_KIND_ID")
    private Integer kindId;


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

    public Integer getStabKindId() {
        return stabKindId;
    }

    public void setStabKindId(Integer stabKindId) {
        this.stabKindId = stabKindId;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
}

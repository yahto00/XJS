package com.pipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipi.entity.admin.User;

import javax.persistence.*;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "t_process_slate")
public class ProcessSlate extends BaseEntity {
    private static final long serialVersionUID = -7732329958431149263L;

    /**
     * 石材主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_process_slate_id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "slate_name", length = 100)
    private String slateName;

    /**
     * 石材单价
     */
    @Column(name = "slate_price")
    private Double price;

//    /** 石材的长度*/
//    @Column(name = "SLATE_LENGTH")
//    private float length;
//
//    /** 石材的宽度*/
//    @Column(name = "SLATE_HEIGHT")
//    private float height;


    /**
     * 板材面积
     */
    @Column(name = "slate_acreage")
    private Float acreage;

    /**
     * 石材所属的加工单编号
     */
    @Column(name = "slate_pro_num")
    private String proNum;

    /**
     * 石材加工单操作人
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_operate_user_id")
    private User user;

    /**
     * 关联所属的扎
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_stab_kind_id")
    @JsonIgnore
    private StabKind stabKind;

    /**
     * 关联所属的种类
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_kind_id")
    @JsonIgnore
    private Kind kind;

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

    //    public float getLength() {
//        return length;
//    }
//
//    public void setLength(float length) {
//        this.length = length;
//    }
//
//    public float getHeight() {
//        return height;
//    }
//
//    public void setHeight(float height) {
//        this.height = height;
//    }
    public Float getAcreage() {
        return acreage;
    }

    public void setAcreage(Float acreage) {
        this.acreage = acreage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    public StabKind getStabKind() {
        return stabKind;
    }

    public void setStabKind(StabKind stabKind) {
        this.stabKind = stabKind;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}

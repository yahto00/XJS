package com.pipi.entity;

import com.pipi.entity.admin.User;

import javax.persistence.*;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "T_PROCESS_SLATE")
public class ProcessSlate extends BaseEntity{
    private static final long serialVersionUID = -7732329958431149263L;

    /** 石材主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "PK_PROCESS_SLATE_ID")
    private Integer id;

    /** 用户名 */
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

    /** 石材所属的加工单编号*/
    @Column(name = "SLATE_PRO_NUM")
    private String proNum;

    /** 石材加工单操作人 */
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_OPERATE_USER_ID")
    private User user;

    /**
     * 关联所属的扎
     */
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_STAB_KIND_ID")
    private StabKind stabKind;

    /**
     * 关联所属的种类
     */
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_KIND_ID")
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

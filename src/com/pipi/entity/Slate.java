package com.pipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "T_SLATE")
public class Slate extends BaseEntity {

    private static final long serialVersionUID = 5926553015764597895L;

    /**
     * 石材主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_SLATE_ID")
    private Integer id;

    /**
     * 石材名
     */
    @Column(name = "SLATE_NAME", length = 100)
    private String slateName;

    /**
     * 石材单价
     */
    @Column(name = "SLATE_PRICE")
    private Double price;

    /**
     * 石材的长度
     */
    @Column(name = "SLATE_LENGTH")
    private float length;

    /**
     * 石材的宽度
     */
    @Column(name = "SLATE_HEIGHT")
    private float height;

    /**
     * 关联所属的扎
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_STAB_KIND_ID")
    @JsonIgnore
    private StabKind stabKind;

    /**
     * 关联所属的种类
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_KIND_ID")
    @JsonIgnore
    private Kind kind;

    /**
     * 如果是回库板材 parentId记录这个板材是来自哪个板材 如果是第一次加板材 parentId=0
     */
    @Column(name = "PARENT_ID")
    private Integer parentId;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}

package com.pipi.entity;

import javax.persistence.*;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "T_DEVELOPMENT")
public class Development extends BaseEntity{
    private static final long serialVersionUID = 59265530157645975L;

    /** 成品主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "PK_DEVELOPMENT_ID")
    private Integer id;

    /** 成品名 */
    @Column(name = "SLATE_NAME", length = 100)
    private String slateName;

    /** 成品单价*/
    @Column(name = "SLATE_PRICE")
    private Double price;

    /** 成品的长度*/
    @Column(name = "SLATE_LENGTH")
    private float length;

    /** 成品的宽度*/
    @Column(name = "SLATE_HEIGHT")
    private float height;

    /** 成品的来源*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_STAB_KIND",foreignKey = @ForeignKey(name = "FK_STAB_KIND"))
    private Slate slate;

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

    public Slate getSlate() {
        return slate;
    }

    public void setSlate(Slate slate) {
        this.slate = slate;
    }
}
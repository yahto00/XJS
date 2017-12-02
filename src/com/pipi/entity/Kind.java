package com.pipi.entity;

import javax.persistence.*;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "t_kind")
public class Kind extends BaseEntity {
    private static final long serialVersionUID = 3956879315775184547L;

    /**
     * 种类ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_kind_id")
    private Integer id;

    /**
     * 种类名称
     */
    @Column(name = "kind_name")
    private String name;

    /**
     * 种类编号
     */
    @Column(name = "kind_num", unique = true)
    private String num;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

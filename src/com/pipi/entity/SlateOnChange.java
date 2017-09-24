package com.pipi.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yahto on 03/05/2017.
 */
@Entity
@Table(name = "T_SLATE_ON_CHANGE")
public class SlateOnChange extends BaseEntity {
    private static final long serialVersionUID = 592530157645975L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_SLATE_ON_CHANGE_ID")
    private Integer id;

    /**
     * 记录表操作描述
     */
    @Column(name = "SLATE_ON_CHANGE_DESCRIPTION")
    private String description;

    /**
     * 操作时间
     */
    @Column(name = "OP_TIME")
    private Date op_time = new Date();

    /**
     * 操作人
     */
    @Column(name = "FK_USER_ID")
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOp_time() {
        return op_time;
    }

    public void setOp_time(Date op_time) {
        this.op_time = op_time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

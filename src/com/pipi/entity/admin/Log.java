package com.pipi.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.pipi.common.constant.SystemConstant;
import com.pipi.entity.BaseEntity;


/**
 * Created by yahto on 07/05/2017.
 */
@Entity
@Table(name = "t_log")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 6920412063790353593L;

    /**
     * 日志的主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_log_id")
    private Integer id;

    /**
     * 操作内容
     */
    @Column(name = "content", length = 2000, nullable = false)
    private String content;

    /**
     * 操作的IP地址
     */
    @Column(name = "log_ip", length = 50, nullable = false)
    private String ip;

    /**
     * 操作的时间
     */
    @Column(name = "submit_date")
    @DateTimeFormat(pattern = SystemConstant.TIME_PATTEN)
    private Date submitDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

}

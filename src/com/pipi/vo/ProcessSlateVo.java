package com.pipi.vo;

import com.pipi.entity.Kind;
import com.pipi.entity.ProcessSlate;
import com.pipi.entity.StabKind;
import com.pipi.entity.admin.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by yahto on 16/09/2017.
 */
public class ProcessSlateVo implements Serializable {

    private static final long serialVersionUID = -5121414509534767367L;

    /**
     * 石材主键
     */
    private Integer slateId;
    /**
     * 扎主键
     */
    private Integer stabKindId;

    /**
     * 石材单价
     */
    private Double price;

    /**
     * 板材面积
     */
    private Float acreage;

    /**
     * 石材所属的加工单编号
     */
    private String proNum;
    /**
     * 操作人
     */
    private String operatorName;
    /**
     * 种类名
     */
    private String kindName;
    /**
     * 扎编号
     */
    private String stabKindNum;

    public Integer getSlateId() {
        return slateId;
    }

    public void setSlateId(Integer slateId) {
        this.slateId = slateId;
    }

    public Integer getStabKindId() {
        return stabKindId;
    }

    public void setStabKindId(Integer stabKindId) {
        this.stabKindId = stabKindId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getAcreage() {
        return acreage;
    }

    public void setAcreage(Float acreage) {
        this.acreage = acreage;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getStabKindNum() {
        return stabKindNum;
    }

    public void setStabKindNum(String stabKindNum) {
        this.stabKindNum = stabKindNum;
    }
}

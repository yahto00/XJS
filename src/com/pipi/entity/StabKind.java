package com.pipi.entity;

import com.pipi.common.constant.SystemConstant;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "T_STAB_KIND")
public class StabKind extends BaseEntity {

    private static final long serialVersionUID = -8307364884116870179L;
    /**
     * 扎主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_STAB_KIND_ID")
    private Integer id;

    /**
     * 扎编号
     */
    @Column(name = "STAB_KIND_NUM")
    private String num;

    /**
     * 扎的入库时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "IN_TIME")
    @DateTimeFormat(pattern = SystemConstant.DATE_PATTEN)
    private Date in_time = new Date();

    /**
     * 扎入库的片数
     */
    @Column(name = "ORIGINAL_COUNT")
    private Integer originalCount;

    /**
     * 扎入库的总面积
     */
    @Column(name = "ORIGINAL_ACREAGE")
    private Float originalAcreage;

    /**
     * 扎在库的片数
     */
    @Column(name = "CURRENT_COUNT")
    private Integer currentCount;

    /**
     * 扎在库的总面积
     */
    @Column(name = "CURRENT_ACREAGE")
    private Float currentAcreage;

    /**
     * 备注
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 出库时间 某一片出库后会动态更新 需要提供set方法
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "OUT_TIME")
    @DateTimeFormat(pattern = SystemConstant.DATE_PATTEN)
    private Date out_time;

    /**
     * 出库片数
     */
    @Column(name = "OUT_COUNT")
    private Integer outCount;

    /**
     * 返库片数
     */
    @Column(name = "BACK_COUNT")
    private Integer backCount;

    /**
     * 出库平方数
     */
    @Column(name = "OUT_ACREAGE")
    private Float outAcreage;

    /**
     * 关联所属的种类
     */
    @Column(name = "FK_KIND_ID")
    private Integer kindId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getOriginalCount() {
        return originalCount;
    }

    public void setOriginalCount(Integer originalCount) {
        this.originalCount = originalCount;
    }

    public Float getOriginalAcreage() {
        return originalAcreage;
    }

    public void setOriginalAcreage(Float originalAcreage) {
        this.originalAcreage = originalAcreage;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public Float getCurrentAcreage() {
        return currentAcreage;
    }

    public void setCurrentAcreage(Float currentAcreage) {
        this.currentAcreage = currentAcreage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOut_time() {
        return out_time;
    }

    public void setOut_time(Date out_time) {
        this.out_time = out_time;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Integer getBackCount() {
        return backCount;
    }

    public void setBackCount(Integer backCount) {
        this.backCount = backCount;
    }

    public Float getOutAcreage() {
        return outAcreage;
    }

    public void setOutAcreage(Float outAcreage) {
        this.outAcreage = outAcreage;
    }

    public Date getIn_time() {
        return in_time;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
}

package com.pipi.entity;

import com.pipi.common.constant.SystemConstant;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yantong on 24/04/2017.
 */
@Entity
@Table(name = "t_stab_kind")
public class StabKind extends BaseEntity {

    private static final long serialVersionUID = -8307364884116870179L;
    /**
     * 扎主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_stab_kind_id")
    private Integer id;

    /**
     * 扎编号
     */
    @Column(name = "stab_kind_num", unique = true)
    private String num;

    /**
     * 扎的入库时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "in_time")
    @DateTimeFormat(pattern = SystemConstant.DATE_PATTEN)
    private Date in_time = new Date();

    /**
     * 扎入库的片数
     */
    @Column(name = "original_count")
    private Integer originalCount;

    /**
     * 扎入库的总面积
     */
    @Column(name = "original_acreage")
    private Float originalAcreage;

    /**
     * 扎在库的片数
     */
    @Column(name = "current_count")
    private Integer currentCount;

    /**
     * 扎在库的总面积
     */
    @Column(name = "current_acreage")
    private Float currentAcreage;

    /**
     * 备注
     */
    @Column(name = "description")
    private String description;

    /**
     * 出库时间 某一片出库后会动态更新 需要提供set方法
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "out_time")
    @DateTimeFormat(pattern = SystemConstant.DATE_PATTEN)
    private Date out_time;

    /**
     * 出库片数
     */
    @Column(name = "out_count")
    private Integer outCount;

    /**
     * 返库片数
     */
    @Column(name = "back_count")
    private Integer backCount;

    /**
     * 出库平方数
     */
    @Column(name = "out_acreage")
    private Float outAcreage;

    /**
     * 关联所属的种类
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_kind_id")
    private Kind kind;

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

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}

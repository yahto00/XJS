package com.pipi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * Created by yahto on 07/05/2017.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1730442499015839066L;

    /**
     * 是否删除: 0 不删除，1 删除
     */
    @Column(name = "isdelete", length = 2, nullable = false, columnDefinition = "INT default 0")
    @JsonIgnore
    private int isDelete = 0;

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

}

package com.pipi.vo;

import com.pipi.entity.StabKind;

import java.io.Serializable;

/**
 * Created by yahto on 02/06/2017.
 */
public class StabKindVO implements Serializable{
    private static final long serialVersionUID = -993044632015839066L;
    private StabKind stabKind;
    private Integer kindId;

    public StabKind getStabKind() {
        return stabKind;
    }

    public void setStabKind(StabKind stabKind) {
        this.stabKind = stabKind;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
}

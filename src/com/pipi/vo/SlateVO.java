package com.pipi.vo;

import com.pipi.entity.Slate;

import java.io.Serializable;

/**
 * Created by yahto on 02/06/2017.
 */
public class SlateVO implements Serializable {
    private static final long serialVersionUID = -173044632015839066L;
    private Slate slate;
    private Integer stabKindId;
    private Integer kindId;

    public Slate getSlate() {
        return slate;
    }

    public void setSlate(Slate slate) {
        this.slate = slate;
    }

    public Integer getStabKindId() {
        return stabKindId;
    }

    public void setStabKindId(Integer stabKindId) {
        this.stabKindId = stabKindId;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
}

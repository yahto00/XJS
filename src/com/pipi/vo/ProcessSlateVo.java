package com.pipi.vo;

import com.pipi.entity.Kind;
import com.pipi.entity.ProcessSlate;
import com.pipi.entity.StabKind;
import com.pipi.entity.admin.User;

import java.io.Serializable;

/**
 * Created by yahto on 16/09/2017.
 */
public class ProcessSlateVo implements Serializable{

    private static final long serialVersionUID = -5121414509534767367L;
    private ProcessSlate processSlate;
    private StabKind stabKind;
    private Kind kind;

    public ProcessSlate getProcessSlate() {
        return processSlate;
    }

    public void setProcessSlate(ProcessSlate processSlate) {
        this.processSlate = processSlate;
    }

    public StabKind getStabKind() {
        return stabKind;
    }

    public void setStabKind(StabKind stabKind) {
        this.stabKind = stabKind;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

}

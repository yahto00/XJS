package com.pipi.vo;

import java.io.Serializable;

/**
 * Created by yahto on 22/10/2017.
 */
public class OrderVo implements Serializable {
    private static final long serialVersionUID = -3331479061439937058L;

    Integer developmentId;

    Integer count;

    public Integer getDevelopmentId() {
        return developmentId;
    }

    public void setDevelopmentId(Integer developmentId) {
        this.developmentId = developmentId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

package com.pipi.service;

import com.pipi.entity.Slate;

/**
 * Created by yahto on 13/05/2017.
 */
public interface ISlateService extends IBaseService {
    /** 增加板材功能*/
    void addSlate(Slate slate, Integer kindId, Integer stabKindId);
}

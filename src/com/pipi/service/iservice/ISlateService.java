package com.pipi.service.iservice;

import com.pipi.entity.Slate;

/**
 * Created by yahto on 13/05/2017.
 */
public interface ISlateService extends IBaseService {
    /** 增加板材功能*/
    void addSlate(Slate slate, Integer kindId, Integer stabKindId,Float loseAcreage);
    /** 批量删除板材*/
    void deleteSlateByIds(Integer[] ids,Integer stabKindId);
}

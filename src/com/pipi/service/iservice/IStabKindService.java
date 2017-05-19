package com.pipi.service.iservice;

import com.pipi.entity.StabKind;

/**
 * Created by yahto on 14/05/2017.
 */
public interface IStabKindService extends IBaseService{
    /** 添加扎*/
    void addStabKind(StabKind stabKind);
    /** 批量删除扎*/
    void deleteStabKindByIds(Integer[] ids);
}

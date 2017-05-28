package com.pipi.service.iservice;

import com.pipi.entity.StabKind;

import java.util.List;

/**
 * Created by yahto on 14/05/2017.
 */
public interface IStabKindService extends IBaseService{
    /** 添加扎*/
    Integer addStabKind(StabKind stabKind);
    /** 批量删除扎*/
    void deleteStabKindByIds(Integer[] ids);
    /** 查询所有扎种类*/
    List<StabKind> queryAllStabKind();
}

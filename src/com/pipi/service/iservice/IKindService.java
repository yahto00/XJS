package com.pipi.service.iservice;

/**
 * Created by yahto on 14/05/2017.
 */
public interface IKindService extends IBaseService {
    /**
     * 批量删除种类
     */
    void deleteKindByIds(Integer[] ids);
}

package com.pipi.service.iservice;

import com.pipi.entity.Kind;
import com.pipi.vo.Page;

import java.util.List;

/**
 * Created by yahto on 14/05/2017.
 */
public interface IKindService extends IBaseService {
    /**
     * 批量删除种类
     */
    void deleteKindByIds(Integer[] ids);

    List<Kind> queryAllKind(Page page);
}

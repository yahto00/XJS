package com.pipi.service.iservice;

import com.pipi.entity.Slate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yahto on 13/05/2017.
 */
public interface ISlateService extends IBaseService {
    /** 增加板材功能*/
    void backSlate(Slate slate, Integer kindId, Integer stabKindId, HttpServletRequest request);
    /** 批量删除板材*/
    void deleteSlateByIds(Integer[] ids,Integer stabKindId, String outNum,HttpServletRequest request);
    /** 在添加扎的时候添加板材*/
    void addSlate(List<Slate> slateList, HttpServletRequest request);
    /** 根据stabKindId查询板材*/
    List<Slate> querySlateByStabKindId(Integer stabKindId);
}

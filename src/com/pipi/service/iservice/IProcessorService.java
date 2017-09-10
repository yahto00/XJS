package com.pipi.service.iservice;

/**
 * Created by yahto on 10/09/2017.
 */
public interface IProcessorService extends IBaseService {
    void backStorage(Integer processSlateId, Integer stabKindId, String description, float length, float width);
}

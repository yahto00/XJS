package com.pipi.service.iservice;

import com.pipi.entity.Development;
import com.pipi.entity.admin.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yahto on 14/10/2017
 */
public interface IDevelopmentService extends IBaseService {
    /**
     * 按用户查询所有的成品
     *
     * @param user
     * @return
     */
    List<Development> queryAllDevelopment(User user);

    /**
     * 出成品
     *
     * @param processSlateId
     * @param list
     * @param request
     */
    void produceDevelopment(Integer processSlateId, List<Development> list, HttpServletRequest request);
}

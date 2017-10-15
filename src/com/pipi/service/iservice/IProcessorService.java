package com.pipi.service.iservice;

import com.pipi.entity.ProcessSlate;
import com.pipi.entity.admin.User;
import com.pipi.vo.Page;
import com.pipi.vo.SlateDataVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yahto on 10/09/2017.
 */
public interface IProcessorService extends IBaseService {
    /**
     * 返库操作
     *
     * @param processSlateId
     * @param stabKindId
     * @param description
     * @param voList
     */
    void backStorage(Integer processSlateId, Integer stabKindId, String description, List<SlateDataVO> voList);

    /**
     * 分页查询加工间板材
     *
     * @param user
     * @param page
     * @return
     */
    List<ProcessSlate> queryProcessSlateByPage(User user, Page page);
}

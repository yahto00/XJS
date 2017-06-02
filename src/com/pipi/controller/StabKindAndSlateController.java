package com.pipi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Kind;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.IKindService;
import com.pipi.service.iservice.ISlateService;
import com.pipi.service.iservice.IStabKindService;
import com.pipi.vo.SlateDataVO;
import com.pipi.vo.SlateVO;
import com.pipi.vo.StabKindVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yahto on 14/05/2017.
 */
@Controller
public class StabKindAndSlateController extends BaseController {
    @Autowired
    private IStabKindService stabKindService;

    @Autowired
    private IKindService kindService;

    @Autowired
    private ISlateService slateService;

    /**
     * 添加扎功能
     *
     * @param data
     * @return
     * @author yahto
     */
    @RequestMapping("stabKindAndSlate_addStabKind.ajax")
    @ResponseBody
    public Map<String, Object> addStabKind(@RequestBody String data, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            //解析json
            JSONObject json = JSON.parseObject(data);
            String num = json.getString("num");
            String description = json.getString("description");
            Kind kind = (Kind) kindService.queryObjectByID(Kind.class, json.getInteger("kindId"));
            if (kind == null)
                throw new BusinessException("未指定该扎的种类");
            JSONArray array = json.getJSONArray("data");
            //得到长宽数据List
            List<SlateDataVO> voList = JSONArray.parseArray(array.toJSONString(), SlateDataVO.class);
            float originalAcreage = getAcreage(voList);
            //把扎信息存入数据库
            StabKind stabKind = new StabKind();
            stabKind.setNum(num);
            stabKind.setOriginalAcreage(originalAcreage);
            stabKind.setDescription(description);
            stabKind.setKind(kind);
            stabKind.setOriginalCount(voList.size());
            stabKind.setCurrentCount(voList.size());
            stabKind.setCurrentAcreage(originalAcreage);
            stabKindService.addStabKind(stabKind);
            //填充Slate信息
            List<Slate> slateList = new ArrayList<Slate>();
            for (SlateDataVO vo : voList) {
                Slate slate = new Slate();
                slate.setStabKind(stabKind);
                slate.setKind(kind);
                slate.setParentId(0);//刚存入 父id 为0
                slate.setSlateName(json.getString("slateName"));
                slate.setPrice(json.getDouble("price"));
                slate.setHeight(vo.getHeight());
                slate.setLength(vo.getLength());
                slateList.add(slate);
            }
            //批量存入数据库
            slateService.addSlate(slateList, request);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "系统错误");
        }
        return map;
    }

    /**
     * 得到板材总面积
     *
     * @param slateDataVOS
     * @return
     * @author yahto
     */
    private float getAcreage(List<SlateDataVO> slateDataVOS) {
        float acreage = 0f;
        for (SlateDataVO vo : slateDataVOS) {
            acreage += vo.getHeight() * vo.getLength();
        }
        return acreage;
    }

    /**
     * 批量删除扎功能
     *
     * @param ids
     * @return
     * @author yahto
     */
    @RequestMapping("stabKindAndSlate_deleteStabKindByIds.ajax")
    @ResponseBody
    public Map<String, Object> deleteStabKindByIds(Integer[] ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            stabKindService.deleteStabKindByIds(ids);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 查询所有扎种类
     *
     * @return
     * @author hbwj
     */
    @RequestMapping("stabKindAndSlate_queryAllStabKind.ajax")
    @ResponseBody
    public Map<String, Object> queryAllStabKind() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            List<StabKind> list = stabKindService.queryAllStabKind();
            map.put("list", list);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }


    /**
     * 根据种类Id和种类编号模糊查询所有扎种类
     *
     * @param id
     * @return
     * @author hbwj
     */
    @RequestMapping("stabKindAndSlate_queryStabKindByKindIdOrNum.ajax")
    @ResponseBody
    public Map<String, Object> queryStabKindByKindIdOrNum(Integer id, String num) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            List<StabKind> stabKindList = stabKindService.queryALLStabKindByKindId(id, num);
            List<StabKindVO> list = new ArrayList<StabKindVO>();
            for (StabKind stabKind : stabKindList) {
                StabKindVO vo = new StabKindVO();
                vo.setStabKind(stabKind);
                vo.setKindId(stabKind.getKind().getId());
                list.add(vo);
            }
            map.put("list", list);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 根据stabKindId 查询stale
     *
     * @param stabKindId
     * @return
     * @author yahto
     */
    @RequestMapping("stabKindAndSlate_querySlateByStabKindId.ajax")
    @ResponseBody
    public Map<String, Object> querySlateByStabKindId(Integer stabKindId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            List<Slate> slateList = slateService.querySlateByStabKindId(stabKindId);
            List<SlateVO> list = new ArrayList<SlateVO>();
            for (Slate slate : slateList) {
                SlateVO vo = new SlateVO();
                vo.setSlate(slate);
                vo.setKindId(slate.getKind().getId());
                vo.setStabKindId(slate.getStabKind().getId());
                list.add(vo);
            }
            map.put("msg", "操作成功");
            map.put("data", true);
            map.put("list", list);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 批量删除板材功能(出库)
     *
     * @param ids
     * @return
     * @author yahto
     */
    @RequestMapping("stabKindAndSlate_deleteSlateByIds")
    @ResponseBody
    public Map<String, Object> deleteSlateByIds(Integer[] ids, Integer stabKindId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            slateService.deleteSlateByIds(ids, stabKindId, request);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 增加回退板材功能
     *
     * @param slate
     * @param kindId
     * @param stabKindId
     * @return
     * @author yahto
     */
    @RequestMapping("stabKindAndSlate_addSlate.ajax")
    @ResponseBody
    public Map<String, Object> addSlate(Slate slate, Integer kindId, Integer stabKindId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            slateService.backSlate(slate, kindId, stabKindId, request);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
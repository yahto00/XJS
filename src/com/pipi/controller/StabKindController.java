package com.pipi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.AntiCollisionHashMap;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Kind;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.IKindService;
import com.pipi.service.iservice.ISlateService;
import com.pipi.service.iservice.IStabKindService;
import com.pipi.util.JsonUtils;
import com.pipi.vo.SlateVO;
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
public class StabKindController extends BaseController {
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
    @RequestMapping("stabKind_addStabKind.ajax")
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
            JSONArray array = json.getJSONArray("data");
            //得到长宽数据List
            List<SlateVO> voList = JSONArray.parseArray(array.toJSONString(), SlateVO.class);
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
            //填充Slate信息
            List<Slate> slateList = new ArrayList<Slate>();
            for (SlateVO vo : voList) {
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
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","系统错误");
        }
        return map;
    }

    /**
     * 得到板材总面积
     *
     * @param slateVOS
     * @return
     * @author yahto
     */
    private float getAcreage(List<SlateVO> slateVOS) {
        float acreage = 0f;
        for (SlateVO vo : slateVOS) {
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
    @RequestMapping("stabKind_deleteStabKindByIds.ajax")
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
    @RequestMapping("stabKind_queryAllStabKind.ajax")
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
}

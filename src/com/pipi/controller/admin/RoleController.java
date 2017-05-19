package com.pipi.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pipi.common.constant.SystemConstant;
import com.pipi.controller.BaseController;
import com.pipi.entity.admin.Role;
import com.pipi.service.iservice.adminIService.IRoleService;
import com.pipi.util.Ufn;
import com.pipi.vo.ListVo;


/**
 * Created by yahto on 07/05/2017.
 */
@Controller
public class RoleController extends BaseController {

	@Resource
	IRoleService roleService;

	/**
	 * 查看角色
	 * 
	 * @param roleName
	 *            通过角色名字模糊查询
	 * @return ListVo<Role>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("role_list.ajax")
	@ResponseBody
	public ListVo<Role> getRoleList(String roleName,Integer start,Integer limit) {
		if(start == null)
			start = 1;
		if(limit == null)
			limit = SystemConstant.PAGE_SIZE;
		/** 返回给前台的数据 */
		ListVo<Role> listVo = new ListVo<Role>();
		/** 得到的展示数据 */
		List<Role> listData = new ArrayList<Role>();
		/** 所有的角色信息 */
		List<Role> all = new ArrayList<Role>();
		try {
			String hql = "from Role r where r.isDelete=0";
			if (roleName != null)
				hql = hql + " and r.roleName like '%" + roleName + "%'";
			all = (List<Role>) roleService.queryObjectList(hql);
			listData = (List<Role>) roleService.findPageByQuery(start,
					limit, hql, null);
			if (listData != null){
				int nLen = listData.size();
				for (int i=0; i<nLen; i++){
					Role roleItem = listData.get(i);
					List<Object> privList = (List<Object>) roleService.queryListByNavtiveSql("select PRIV_ID from t_role_priv where FK_ROLE_ID= "+roleItem.getId());
					if (privList != null)
						roleItem.setPrivs(Ufn.join(privList));
				}
			}
			listVo.setTotalSize(all.size());
			listVo.setList(listData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVo;
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return map
	 */
	@RequestMapping("role_add.ajax")
	@ResponseBody
	public Map<String, Object> addRole(Role role) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.addRole(role);
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "角色名重复或系统错误，请重新尝试");
		}
		return map;
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return map
	 */
	@RequestMapping("role_update.ajax")
	@ResponseBody
	public Map<String, Object> updateRole(Role role) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.updateRole(role);
			map.put("success", true);
			map.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "角色名重复或系统错误，请重新尝试");
		}
		return map;
	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 *            待删除角色的id数组
	 * @return map
	 */
	@RequestMapping("role_delete.ajax")
	@ResponseBody
	public Map<String, Object> deleteRoles(String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ids.length - 1; i++)
				sb.append(ids[i] + ",");
			sb.append(ids[ids.length - 1]);
			roleService.deleteRoles(sb.toString());
			map.put("success", true);
			map.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "系统错误，请重新尝试");
		}
		return map;
	}
}

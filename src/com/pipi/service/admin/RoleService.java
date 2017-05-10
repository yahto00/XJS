package com.pipi.service.admin;

import org.springframework.stereotype.Service;

import com.pipi.common.logaop.MyLog;
import com.pipi.entity.admin.Role;
import com.pipi.service.BaseService;

/**
 * @author liuyang
 */
@Service
public class RoleService extends BaseService implements IRoleService {

	@Override
	@MyLog(operationName = "添加角色", operationType = "add")
	public void addRole(Role role) {
		add(role);
	}

	@Override
	@MyLog(operationName = "更新角色", operationType = "update")
	public void updateRole(Role role) {
		update(role);
		executeNativeSql("delete from t_role_priv where FK_ROLE_ID=" + role.getId());
		String priv = role.getPrivs();
		if (priv != null && !priv.equals("")){
			String[] vList = priv.split(",");
			for (int i=0; i<vList.length; i++)
				executeNativeSql("insert into t_role_priv(FK_ROLE_ID, PRIV_ID) values ("+role.getId()+","+vList[i]+")");
		}
	}

	@Override
	@MyLog(operationName = "批量删除角色", operationType = "delete")
	public void deleteRoles(String ids) {
		executeNativeSql("delete from t_role_priv where FK_ROLE_ID in (" + ids + ")");
		delete(Role.class, ids);
	}

}

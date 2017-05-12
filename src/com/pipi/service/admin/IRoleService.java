package com.pipi.service.admin;

import com.pipi.entity.admin.Role;
import com.pipi.service.IBaseService;


/**
 * Created by yahto on 07/05/2017.
 */
public interface IRoleService extends IBaseService {

	void addRole(Role role);

	void updateRole(Role role);

	void deleteRoles(String ids);
}

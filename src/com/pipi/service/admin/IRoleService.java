package com.pipi.service.admin;

import com.pipi.entity.admin.Role;
import com.pipi.service.IBaseService;

/**
 * @author liuyang
 */
public interface IRoleService extends IBaseService {

	void addRole(Role role);

	void updateRole(Role role);

	void deleteRoles(String ids);
}

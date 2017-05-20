package com.pipi.vo;

import com.pipi.entity.admin.Role;
import com.pipi.entity.admin.User;

import java.util.List;

/**
 * Created by yahto on 20/05/2017.
 */
public class UserRoleVo {
    private User user;
    private List<Role> roleList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}

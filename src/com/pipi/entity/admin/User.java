package com.pipi.entity.admin;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pipi.entity.BaseEntity;


/**
 * Created by yahto on 07/05/2017.
 */
@Entity
@Table(name = "T_USER")
public class User extends BaseEntity {

    private static final long serialVersionUID = 487092737452798527L;

    /**
     * 用户主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_USER_ID")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "USER_NAME", length = 100)
    private String userName;

    /**
     * 登录名
     */
    @Column(name = "LOGIN_NAME", length = 50, nullable = false, unique = true)
    private String loginName;


    /**
     * 密码
     */
    @Column(name = "PASSWORD", length = 50, nullable = false)
    @JsonIgnore
    //@Size(min=5,max=16,message="密码长度为5到16位")
    private String password;

    @Transient
    private Set<Integer> roles;    // 角色表

    @Transient
    private Set<Integer> privs;    // 权限表

    public Set<Integer> getPrivs() {
        return privs;
    }

    public void setPrivs(Set<Integer> privs) {
        this.privs = privs;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object user) {
        return user instanceof User && ((User) user).getId().equals(getId());
    }
}

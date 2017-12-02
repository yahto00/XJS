package com.pipi.entity.admin;

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
@Table(name = "t_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = -1717004778758331004L;

    /**
     * 角色主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_role_id")
    private Integer id;

    /**
     * 角色名
     */
    @Column(name = "role_name", length = 50, unique = true, nullable = false)
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "description", length = 300)
    private String description;

    /**
     * 权限，以逗号隔开多个权限
     */
    @JsonIgnore
    @Transient
    private String privs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrivs() {
        return privs;
    }

    public void setPrivs(String privs) {
        this.privs = privs;
    }
}

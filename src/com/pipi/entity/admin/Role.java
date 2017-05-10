package com.pipi.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pipi.entity.BaseEntity;


/**
 * 角色实体类
 */
@Entity
@Table(name = "T_ROLE")
public class Role extends BaseEntity {

	private static final long serialVersionUID = -1717004778758331004L;
	
	/** 角色主键 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK_ROLE_ID")
	private Integer id;

	/** 角色名 */
	@Column(name = "ROLE_NAME", length = 50, unique = true, nullable = false)
	private String roleName;

	/** 角色描述 */
	@Column(name = "DESCRIPTION", length = 300)
	private String description;

	/** 权限，以逗号隔开多个权限 */
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

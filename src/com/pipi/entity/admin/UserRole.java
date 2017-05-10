package com.pipi.entity.admin;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *	用户角色实体类
 */
@Entity
@Table(name = "T_USER_ROLE")
public class UserRole implements Serializable {

	private static final long serialVersionUID = -4892332844254013966L;
	
	/** 用户角色表主键*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK_USER_ROLE_REF_ID")
	private Integer id;
	
	/**用户外键 */
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FK_USER_ID")
	private User user;
	
	/** 角色外键*/
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FK_ROLE_ID")
	private Role role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}

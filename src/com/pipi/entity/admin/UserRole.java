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
 * Created by yahto on 07/05/2017.
 */
@Entity
@Table(name = "t_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -4892332844254013966L;

    /**
     * 用户角色表主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_user_role_ref_id")
    private Integer id;

    /**
     * 用户外键
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_user_id")
    private User user;

    /**
     * 角色外键
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_role_id")
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

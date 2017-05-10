package com.pipi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/** 系统公共字段实体类 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -1730442499015839066L;

	/**  是否删除: 0 不删除，1 删除 */
	@Column(name = "ISDELETE", length = 2)
	private int isDelete = 0;

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}

package com.pipi.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.pipi.common.constant.SystemConstant;
import com.pipi.entity.BaseEntity;


/**
 * 字典表
 */
@Entity
@Table(name = "T_DICTIONARY")
public class Dictionary extends BaseEntity{

	private static final long serialVersionUID = -4843286092042358897L;
	
	/** 字典主键*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK_DICTIONARY_ID")
	private Integer id;
	
	/** 名称*/
	@Column(name = "NAME", length = 200)
	private String name;
	
	/** 编码，在XML文件中配置*/
	@Column(name = "CODE", length = 200)
	private String code;
	
	/** 排序或等级 */
	@Column(name = "SORT")
	private Integer sort;
	
	/**操作的时间*/
	@Column(name = "SUBMIT_DATE")
	@DateTimeFormat(pattern=SystemConstant.TIME_PATTEN)
	private Date submitDate;
	
	/** 备注：网点号*/
	@Column(name = "REMARKS", length = 200)
	private String remarks;
	
	/** 备注2*/
	@Column(name = "REMARKS2", length = 200)
	private String remarks2;
	
	/** 备注3*/
	@Column(name = "REMARKS3", length = 200)
	private String remarks3;
	
	/** 备注4*/
	@Column(name = "REMARKS4", length = 200)
	private String remarks4;
	
	/** 备注5*/
	@Column(name = "REMARKS5", length = 200)
	private String remarks5;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getRemarks2() {
		return remarks2;
	}

	public String getRemarks3() {
		return remarks3;
	}

	public String getRemarks4() {
		return remarks4;
	}

	public String getRemarks5() {
		return remarks5;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}

	public void setRemarks3(String remarks3) {
		this.remarks3 = remarks3;
	}

	public void setRemarks4(String remarks4) {
		this.remarks4 = remarks4;
	}

	public void setRemarks5(String remarks5) {
		this.remarks5 = remarks5;
	}
}

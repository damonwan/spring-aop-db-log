package com.damon.example.aoplog.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.damon.example.aoplog.annotation.Column;

@MappedSuperclass
public class BaseEntity extends Base {

    private static final long serialVersionUID = 4440222306509353388L;

    @Column(name = "id", description = "ID")
    @Id
    @GeneratedValue
	private Long id;
	
	/**
	 * 创建时间
	 * */
	@Column(name = "gmt_create", description = "创建时间")
	private Date gmtCreate;

	/**
	 * 上次修改时间
	 * */
	@Column(name = "gmt_modified", description = "上次修改时间")
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}

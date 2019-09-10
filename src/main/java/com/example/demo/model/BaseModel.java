package com.example.demo.model;

import com.example.demo.myenum.StatusEnum;

import java.util.Date;

public class BaseModel {
	
	private int id;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private String updateUser;
	private int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status.getValue();
	}
	
}

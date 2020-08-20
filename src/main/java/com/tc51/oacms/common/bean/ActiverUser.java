package com.tc51.oacms.common.bean;

import com.tc51.oacms.system.domain.User;

import java.util.List;


public class ActiverUser {

	//存储用户基本信息
	private User user;
	// 存储他拥有哪些角色
	private List<String> roles;

	//存储有哪些权限
	private List<String> permissions;




	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}

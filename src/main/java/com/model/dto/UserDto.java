package com.model.dto;

import java.io.Serializable;
import java.util.Collection;

import com.model.RoleEntity;

public class UserDto  implements Serializable{

	private static final long serialVersionUID = 6835192601898364280L;
	private String email;
	private String password;
	private Collection<RoleEntity> roles;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<RoleEntity> getRoles() {
		return roles;
	}
	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
	}
	
	
}

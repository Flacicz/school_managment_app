package com.example.schoolManagment.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_USER, ROLE_ADMIN, ROLE_STUDENT, ROLE_TEACHER;

	@Override
	public String getAuthority() {
		return name();
	}
}

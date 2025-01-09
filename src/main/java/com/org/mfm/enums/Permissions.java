package com.org.mfm.enums;

import lombok.Getter;

public enum Permissions {

	ADMIN_CREATE("admin:create"), ADMIN_DELETE("admin:delete"), ADMIN_READ("admin:read"), ADMIN_UPDATE("admin:update"),
	MANAGER_CREATE("management:create"), MANAGER_DELETE("management:delete"), MANAGER_READ("management:read"),
	MANAGER_UPDATE("management:update");

	@Getter
	private final String permission;

	Permissions(String permission) {
		this.permission = permission;
	}

}

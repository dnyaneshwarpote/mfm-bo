package com.org.mfm.enums;

import lombok.Getter;

public enum Permissions {

	ADMIN_READ("admin:read"), ADMIN_UPDATE("admin:update"), ADMIN_CREATE("admin:create"), ADMIN_DELETE("admin:delete"),
	MANAGER_READ("management:read"), MANAGER_UPDATE("management:update"), MANAGER_CREATE("management:create"),
	MANAGER_DELETE("management:delete");

	@Getter
	private final String permission;

	Permissions(String permission) {
		this.permission = permission;
	}

}

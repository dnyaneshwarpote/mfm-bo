package com.org.mfm.enums;

import static com.org.mfm.enums.Permissions.ADMIN_CREATE;
import static com.org.mfm.enums.Permissions.ADMIN_DELETE;
import static com.org.mfm.enums.Permissions.ADMIN_READ;
import static com.org.mfm.enums.Permissions.ADMIN_UPDATE;
import static com.org.mfm.enums.Permissions.MANAGER_CREATE;
import static com.org.mfm.enums.Permissions.MANAGER_DELETE;
import static com.org.mfm.enums.Permissions.MANAGER_READ;
import static com.org.mfm.enums.Permissions.MANAGER_UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

public enum Role {

	USER(Collections.emptySet()), ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE, MANAGER_READ,
			MANAGER_UPDATE, MANAGER_DELETE, MANAGER_CREATE)),
	MANAGER(Set.of(MANAGER_READ, MANAGER_UPDATE, MANAGER_DELETE, MANAGER_CREATE))

	;

	Role(Set<Permissions> permissions) {
		this.permissions = permissions;
	}

	@Getter
	private final Set<Permissions> permissions;

	
}
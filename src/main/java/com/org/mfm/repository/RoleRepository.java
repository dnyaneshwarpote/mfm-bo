package com.org.mfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);

}

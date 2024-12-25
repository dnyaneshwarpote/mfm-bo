package com.org.mfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.MfmUser;

public interface MfmUserRepository extends JpaRepository<MfmUser, Integer> {

}

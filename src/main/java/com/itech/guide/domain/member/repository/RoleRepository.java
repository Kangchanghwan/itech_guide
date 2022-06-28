package com.itech.guide.domain.member.repository;

import com.itech.guide.domain.member.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,String> {



}

package com.f89497.CSCB025_LogisticCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f89497.CSCB025_LogisticCompany.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
    Role findOneByName(String name);
}

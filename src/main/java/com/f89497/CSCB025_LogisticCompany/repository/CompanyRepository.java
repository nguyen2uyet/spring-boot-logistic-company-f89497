package com.f89497.CSCB025_LogisticCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f89497.CSCB025_LogisticCompany.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{
    
}

package com.f89497.CSCB025_LogisticCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f89497.CSCB025_LogisticCompany.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    Customer findOneByUsername(String username);
}

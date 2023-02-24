package com.f89497.CSCB025_LogisticCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f89497.CSCB025_LogisticCompany.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    Employee findOneByUsername(String username);
}

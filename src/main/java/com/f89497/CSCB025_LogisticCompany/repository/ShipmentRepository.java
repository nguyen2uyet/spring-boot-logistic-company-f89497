package com.f89497.CSCB025_LogisticCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f89497.CSCB025_LogisticCompany.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment,Long>{
    
}

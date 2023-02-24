package com.f89497.CSCB025_LogisticCompany.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "offices")
public class Office {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "office")
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    @OneToMany(mappedBy = "fromOffice")
    private List<Shipment> sendShipments;

    @OneToMany(mappedBy = "toOffice")
    private List<Shipment> receiveShipments;
}

package com.f89497.CSCB025_LogisticCompany.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "age",nullable = false)
    private int age;

    @Column(name = "telephone",nullable = false)
    private String telephone;

    @Column(name = "email" , nullable = false)
    private String email;

    @OneToMany(mappedBy="sender")
    private List<Shipment> sendShipments;

    @OneToMany(mappedBy="receiver")
    private List<Shipment> receiveShipments;

}

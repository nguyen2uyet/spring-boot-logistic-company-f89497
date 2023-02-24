package com.f89497.CSCB025_LogisticCompany.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name="username",nullable = false)
    private String username;

    @Column(name = "salary",nullable = false)
    private double salary;

    @Column(name = "email",nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name="office_id")
    private Office office;

}

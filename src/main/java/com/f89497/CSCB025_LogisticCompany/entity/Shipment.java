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
@Table(name="shipments")
public class Shipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "cash_on_delivery")
    private double cashOnDelivery;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name="receiver_id", nullable=false)
    private Customer receiver;

    @ManyToOne
    @JoinColumn(name="fromOffice_id", nullable=false)
    private Office fromOffice;

    @ManyToOne
    @JoinColumn(name="toOffice_id", nullable=false)
    private Office toOffice;
}

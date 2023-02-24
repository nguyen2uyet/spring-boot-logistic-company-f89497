package com.f89497.CSCB025_LogisticCompany.dto;

import lombok.Data;

@Data
public class ShipmentDTO {
    
    private Long id;
    private String type;
    private double cashOnDelivery;
    private Long sender_id;
    private Long receiver_id;
    private Long fromOffice_id;
    private Long toOffice_id;
    private String sender_name;
    private String receiver_name;
    private String fromOffice_address;
    private String toOffice_address;
}

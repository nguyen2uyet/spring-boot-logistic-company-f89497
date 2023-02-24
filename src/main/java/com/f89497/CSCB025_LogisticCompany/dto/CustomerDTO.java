package com.f89497.CSCB025_LogisticCompany.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String username;
    private int age;
    private String telephone;
    private String email;
    private String password;
    private List<ShipmentDTO> sendShipments;
    private List<ShipmentDTO> receiveShipments;
}

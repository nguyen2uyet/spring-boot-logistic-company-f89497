package com.f89497.CSCB025_LogisticCompany.dto;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private String username;
    private double salary;
    private String email;
    private Long officeId;
    private String officeName;
}

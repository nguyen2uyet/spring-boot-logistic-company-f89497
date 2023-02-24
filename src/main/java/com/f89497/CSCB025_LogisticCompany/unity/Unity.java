package com.f89497.CSCB025_LogisticCompany.unity;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.f89497.CSCB025_LogisticCompany.dto.CustomerDTO;
import com.f89497.CSCB025_LogisticCompany.dto.EmployeeDTO;
import com.f89497.CSCB025_LogisticCompany.dto.OfficeDTO;
import com.f89497.CSCB025_LogisticCompany.dto.ShipmentDTO;
import com.f89497.CSCB025_LogisticCompany.dto.UserDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Customer;
import com.f89497.CSCB025_LogisticCompany.entity.Employee;
import com.f89497.CSCB025_LogisticCompany.entity.Office;
import com.f89497.CSCB025_LogisticCompany.entity.Shipment;
import com.f89497.CSCB025_LogisticCompany.entity.User;
import com.f89497.CSCB025_LogisticCompany.repository.CustomerRepository;
import com.f89497.CSCB025_LogisticCompany.repository.OfficeRepository;

@Component
public class Unity {

    private static CustomerRepository customerRepository;

    @Autowired
    private CustomerRepository customerRepository0;

    private static OfficeRepository officeRepository;

    @Autowired
    private OfficeRepository officeRepository0;

    @PostConstruct     
    private void initStaticDao () {
       customerRepository = this.customerRepository0;
       officeRepository = this.officeRepository0;
    }
    
    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toList());
    }

    public static Customer mapCustomerDTOToCustomer(CustomerDTO customerDto,Customer customer){
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setAge(customerDto.getAge());
        customer.setTelephone(customerDto.getTelephone());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }

    public static ShipmentDTO mapShipmentToShipmentDTO(Shipment shipment){
        ModelMapper modelMapper = new ModelMapper();
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO = modelMapper.map(shipment,ShipmentDTO.class);
        shipmentDTO.setReceiver_id(shipment.getReceiver().getId());
        shipmentDTO.setSender_id(shipment.getSender().getId());
        shipmentDTO.setFromOffice_id(shipment.getFromOffice().getId());
        shipmentDTO.setToOffice_id(shipment.getToOffice().getId());
        shipmentDTO.setReceiver_name(shipment.getReceiver().getName());
        shipmentDTO.setSender_name(shipment.getSender().getName());
        shipmentDTO.setFromOffice_address(shipment.getFromOffice().getAddress());
        shipmentDTO.setToOffice_address(shipment.getToOffice().getAddress());
        return shipmentDTO;
    }

    public static List<ShipmentDTO> mapListShipmentDTOs(List<Shipment> shipments){
        return shipments
        .stream()
        .map( e -> mapShipmentToShipmentDTO(e))
        .collect(Collectors.toList());
    }

    public static Shipment mapShipmentDTOtoShipment(ShipmentDTO shipmentDTO,Shipment shipment){
        ModelMapper modelMapper = new ModelMapper();
        shipment = modelMapper.map(shipmentDTO,Shipment.class);
        Customer sender = customerRepository.findById(shipmentDTO.getSender_id()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + shipmentDTO.getSender_id()));
        shipment.setSender(sender);
        Customer receiver = customerRepository.findById(shipmentDTO.getReceiver_id()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + shipmentDTO.getReceiver_id()));
        shipment.setReceiver(receiver);
        Office fromOffice = officeRepository.findById(shipmentDTO.getFromOffice_id()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + shipmentDTO.getFromOffice_id()));
        shipment.setFromOffice(fromOffice);
        Office toOffice = officeRepository.findById(shipmentDTO.getToOffice_id()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + shipmentDTO.getToOffice_id()));
        shipment.setToOffice(toOffice);
        return shipment;
    }

    public static EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
        if(employee.getOffice().getId() != null){
            employeeDTO.setOfficeId(employee.getOffice().getId());
        }
        if(employee.getOffice().getAddress() != null){
            employeeDTO.setOfficeName(employee.getOffice().getAddress());
        }
        return employeeDTO;
    }

    public static List<EmployeeDTO> mapListEmployeeDTOs(List<Employee> employees){
        return employees
        .stream()
        .map( e -> mapEmployeeToEmployeeDTO(e))
        .collect(Collectors.toList());
    }

    public static OfficeDTO mapOfficeToOfficeDTO(Office office){
        ModelMapper modelMapper = new ModelMapper();
        OfficeDTO officeDTO = modelMapper.map(office,OfficeDTO.class);
        if(office.getCompany().getId() != null){
            officeDTO.setCompany_id(office.getCompany().getId());
        }
        if(office.getCompany().getName() != null){
            officeDTO.setCompany_name(office.getCompany().getName());
        }
        return officeDTO;
    }

    public static List<OfficeDTO> mapListOfficeDTOs(List<Office> offices){
        return offices
        .stream()
        .map( e -> mapOfficeToOfficeDTO(e))
        .collect(Collectors.toList());
    }

    public static Customer mapUserDtoToCustomer(UserDTO userDTO){
        Customer customer = new Customer();
        customer.setAge(0);
        customer.setName("");
        customer.setEmail(userDTO.getEmail());
        customer.setUsername(userDTO.getUsername());
        customer.setTelephone("");
        return customer;
    }

}

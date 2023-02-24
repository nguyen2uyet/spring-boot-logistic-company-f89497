package com.f89497.CSCB025_LogisticCompany.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f89497.CSCB025_LogisticCompany.dto.CustomerDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Customer;
import com.f89497.CSCB025_LogisticCompany.entity.Shipment;
import com.f89497.CSCB025_LogisticCompany.repository.CustomerRepository;
import com.f89497.CSCB025_LogisticCompany.repository.ShipmentRepository;
import com.f89497.CSCB025_LogisticCompany.unity.Unity;

@Service
public class CustomerService extends AbstractSevice<CustomerDTO>{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;


    @Override
    public CustomerDTO findOneById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        ModelMapper mapper = new ModelMapper();
        CustomerDTO customerDTO = mapper.map(customer,CustomerDTO.class);
        customerDTO.setReceiveShipments(Unity.mapListShipmentDTOs(customer.getReceiveShipments()));
        customerDTO.setSendShipments(Unity.mapListShipmentDTOs(customer.getSendShipments()));
        return customerDTO;
    }

    public CustomerDTO findOneByUsername(String username){
        Customer customer = customerRepository.findOneByUsername(username);
        ModelMapper mapper = new ModelMapper();
        CustomerDTO customerDTO = mapper.map(customer,CustomerDTO.class);
        customerDTO.setReceiveShipments(Unity.mapListShipmentDTOs(customer.getReceiveShipments()));
        customerDTO.setSendShipments(Unity.mapListShipmentDTOs(customer.getSendShipments()));
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> list() {
        return Unity.mapList(customerRepository.findAll(), CustomerDTO.class);
    }

    @Override
    public void add(CustomerDTO customerDTO) {
        ModelMapper mapper = new ModelMapper();
        customerRepository.save(mapper.map(customerDTO,Customer.class));
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        customer.getSendShipments().stream().forEach(e -> {
            if (e.getId() != null) 
                shipmentRepository.deleteById(e.getId());
        } );
        customer.getReceiveShipments().stream().forEach(e -> {
            if (e.getId() != null) 
                shipmentRepository.deleteById(e.getId());
        } );
        customerRepository.deleteById(id);
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        Customer oldCustomer = customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + customerDTO.getId()));
        oldCustomer = Unity.mapCustomerDTOToCustomer(customerDTO, oldCustomer);
        customerRepository.save(oldCustomer);
    }
    
}

package com.f89497.CSCB025_LogisticCompany.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.f89497.CSCB025_LogisticCompany.dto.CustomerDTO;
import com.f89497.CSCB025_LogisticCompany.dto.ShipmentDTO;
import com.f89497.CSCB025_LogisticCompany.security.MyUserDetails;
import com.f89497.CSCB025_LogisticCompany.service.CustomerService;
import com.f89497.CSCB025_LogisticCompany.service.OfficeService;
import com.f89497.CSCB025_LogisticCompany.service.ShipmentService;

@Controller
public class CustomerController extends AbstractController<CustomerDTO>{

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/customers")
    private String customers(Model model){
        List<CustomerDTO> list = customerService.list();
        model.addAttribute("customers", list);
        return "customer/customers";
    }

    @GetMapping("/add/customer")
    private String showFromAddCustomer(Model model){
        model.addAttribute("customer", new CustomerDTO());
        return "customer/new-customer";
    }

    @Override
    @PostMapping("/add/customer")
    public String add(@ModelAttribute CustomerDTO customer, Model model) {
        customerService.add(customer);
        return "redirect:/customers";
    }

    @Override
    @PostMapping("/update/customer") 
    public String update(@ModelAttribute CustomerDTO customer, Model model) {
        customerService.update(customer);
        return "redirect:/customers";
    }

    @Override
    @GetMapping("/delete/customer/{id}")
    public String delete(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        customerService.delete(id);
        return "redirect:/customers";
    }

    @Override
    @GetMapping("/edit/customer/{id}")
    public String edit(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        model.addAttribute("customer",customerService.findOneById(id));
        return "customer/edit-customer";
    }

    @GetMapping("/customer/sent-list/{id}")
    public String sentList(@PathVariable("id") String strID,Model model){
        Long id = Long.parseLong(strID);
        model.addAttribute("shipments", customerService.findOneById(id).getSendShipments());
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        model.addAttribute("customer", customerDTO);
        return "customer/sent-list";
    }

    @GetMapping("/customer/received-list/{id}")
    public String receivedList(@PathVariable("id") String strID,Model model){
        Long id = Long.parseLong(strID);
        model.addAttribute("shipments", customerService.findOneById(id).getReceiveShipments());
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        model.addAttribute("customer", customerDTO);
        return "customer/received-list";
    }

    @GetMapping("/add/customer/shipment/sent-list/{id}")
    public String addToSentList(@PathVariable("id") String strID,Model model){
        Long id = Long.parseLong(strID);
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setSender_id(id);
        shipmentDTO.setSender_name(customerService.findOneById(id).getName());
        model.addAttribute("shipment", shipmentDTO);
        model.addAttribute("customers", customerService.list());
        model.addAttribute("offices", officeService.list());
        return "customer/add-to-sent-list";
    }

    @GetMapping("add/customer/shipment/received-list/{id}")
    public String addToReceivedList(@PathVariable("id") String strID,Model model){
        Long id = Long.parseLong(strID);
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setReceiver_id(id);
        shipmentDTO.setReceiver_name(customerService.findOneById(id).getName());
        model.addAttribute("shipment", shipmentDTO);
        model.addAttribute("customers", customerService.list());
        model.addAttribute("offices", officeService.list());
        return "customer/add-to-received-list";
    }

    @PostMapping("add/customer/shipment/sent-list/{id}")
    public String addedToSentList(@PathVariable("id") String strID,@ModelAttribute ShipmentDTO shipmentDTO,Model model){
        shipmentDTO.setSender_id(Long.parseLong(strID));
        shipmentService.add(shipmentDTO);
        return "redirect:/customer/sent-list/"+strID;
    }

    @PostMapping("add/customer/shipment/received-list/{id}")
    public String addedToReceivedList(@PathVariable("id") String strID,@ModelAttribute ShipmentDTO shipmentDTO,Model model){
        shipmentDTO.setReceiver_id(Long.parseLong(strID));
        shipmentDTO.setId(null);
        shipmentService.add(shipmentDTO);
        return "redirect:/customer/received-list/"+strID;
    }

    @GetMapping("/customer/shipment/{username}")
    public String showShipmentsCustomer(@PathVariable("username") String username,Model model){
        MyUserDetails principal = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            CustomerDTO customerDTO = customerService.findOneByUsername(username);
            List<ShipmentDTO> listOne = customerDTO.getReceiveShipments();
            List<ShipmentDTO> listTwo = customerDTO.getSendShipments();
            List<ShipmentDTO> newList = Stream.concat(listOne.stream(), listTwo.stream())
                             .collect(Collectors.toList());
            model.addAttribute("shipments", newList); 
        }else{
            return null;
        }                
        return "/customer/customer-shipments";
    }

    @GetMapping("/customer/{username}")
    public String showCustomer(@PathVariable("username") String username,Model model){
        MyUserDetails principal = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            CustomerDTO customerDTO = customerService.findOneByUsername(username);
        model.addAttribute("customer", customerDTO);
        }else{
            return null;
        }
        return "/customer/customer";
    }
    
}

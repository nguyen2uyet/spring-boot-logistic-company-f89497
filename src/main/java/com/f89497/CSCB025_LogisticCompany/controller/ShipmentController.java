package com.f89497.CSCB025_LogisticCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.f89497.CSCB025_LogisticCompany.dto.ShipmentDTO;
import com.f89497.CSCB025_LogisticCompany.service.CustomerService;
import com.f89497.CSCB025_LogisticCompany.service.OfficeService;
import com.f89497.CSCB025_LogisticCompany.service.ShipmentService;

@Controller
public class ShipmentController extends AbstractController<ShipmentDTO>{

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OfficeService officeService;

    @GetMapping("/shipments")
    private String shipments(Model model){
        model.addAttribute("shipments", shipmentService.list());
        return "shipment/shipments";
    }

    @GetMapping("/add/shipment")
    private String showFromAddShipment(Model model){
        model.addAttribute("shipment", new ShipmentDTO());
        model.addAttribute("customers", customerService.list());
        model.addAttribute("offices", officeService.list());
        return "shipment/new-shipment";
    }

    @Override
    @PostMapping("/add/shipment")
    public String add(@ModelAttribute ShipmentDTO shipment, Model model) {
        shipmentService.add(shipment);
        return "redirect:/shipments";
    }

    @Override
    @PostMapping("/update/shipment") 
    public String update(@ModelAttribute ShipmentDTO shipment, Model model) {
        shipmentService.update(shipment);
        return "redirect:/shipments";
    }

    @Override
    @GetMapping("/delete/shipment/{id}")
    public String delete(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        shipmentService.delete(id);
        return "redirect:/shipments";
    }

    @Override
    @GetMapping("/edit/shipment/{id}")
    public String edit(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        model.addAttribute("shipment",shipmentService.findOneById(id));
        model.addAttribute("customers", customerService.list());
        model.addAttribute("offices", officeService.list());
        return "shipment/edit-shipment";
    }

}

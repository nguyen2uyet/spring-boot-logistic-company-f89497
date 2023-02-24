package com.f89497.CSCB025_LogisticCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.f89497.CSCB025_LogisticCompany.dto.OfficeDTO;
import com.f89497.CSCB025_LogisticCompany.service.CompanyService;
import com.f89497.CSCB025_LogisticCompany.service.OfficeService;

@Controller
public class OfficeController extends AbstractController<OfficeDTO>{

    @Autowired
    private OfficeService officeService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/offices")
    private String offices(Model model){
        model.addAttribute("offices", officeService.list());
        return "office/offices";
    }

    @GetMapping("/add/office")
    private String showFromAddOffice(Model model){
        model.addAttribute("office", new OfficeDTO());
        model.addAttribute("companies", companyService.list());
        return "office/new-office";
    }

    @Override
    @PostMapping("/add/office")
    public String add(@ModelAttribute OfficeDTO office, Model model) {
        officeService.add(office);
        return "redirect:/offices";
    }

    @Override
    @PostMapping("/update/office") 
    public String update(@ModelAttribute OfficeDTO office, Model model) {
        officeService.update(office);
        return "redirect:/offices";
    }

    @Override
    @GetMapping("/delete/office/{id}")
    public String delete(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        officeService.delete(id);
        return "redirect:/offices";
    }

    @Override
    @GetMapping("/edit/office/{id}")
    public String edit(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        model.addAttribute("office",officeService.findOneById(id));
        return "office/edit-office";
    }

}

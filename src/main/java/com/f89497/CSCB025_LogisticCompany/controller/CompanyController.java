package com.f89497.CSCB025_LogisticCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.f89497.CSCB025_LogisticCompany.dto.CompanyDTO;
import com.f89497.CSCB025_LogisticCompany.service.CompanyService;

@Controller
public class CompanyController extends AbstractController<CompanyDTO>{

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    private String companies(Model model){
        model.addAttribute("companies", companyService.list());
        return "/company/companies";
    }

    @GetMapping("/add/company")
    private String showFromAddCompany(Model model){
        model.addAttribute("company", new CompanyDTO());
        return "company/new-company";
    }

    @Override
    @PostMapping("/add/company")
    public String add(@ModelAttribute CompanyDTO company, Model model) {
        companyService.add(company);
        return "redirect:/companies";
    }

    @Override
    @PostMapping("/update/company") 
    public String update(@ModelAttribute CompanyDTO company, Model model) {
        companyService.update(company);
        return "redirect:/companies";
    }

    @Override
    @GetMapping("/delete/company/{id}")
    public String delete(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        companyService.delete(id);
        return "redirect:/companies";
    }

    @Override
    @GetMapping("/edit/company/{id}")
    public String edit(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        model.addAttribute("company",companyService.findOneById(id));
        return "company/edit-company";
    }
    
}

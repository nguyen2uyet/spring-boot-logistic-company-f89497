package com.f89497.CSCB025_LogisticCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.f89497.CSCB025_LogisticCompany.dto.EmployeeDTO;
import com.f89497.CSCB025_LogisticCompany.entity.User;
import com.f89497.CSCB025_LogisticCompany.security.MyUserDetails;
import com.f89497.CSCB025_LogisticCompany.service.EmployeeService;
import com.f89497.CSCB025_LogisticCompany.service.OfficeService;

@Controller
public class EmployeeController extends AbstractController<EmployeeDTO>{

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OfficeService officeService;

    @GetMapping("/employees")
    private String employees(Model model){
        model.addAttribute("employees", employeeService.list());
        return "employee/employees";
    }

    @GetMapping("/employee/{username}")
    private String employee(@PathVariable("username") String username,Model model){
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.getUsername().equals(username)){
            model.addAttribute("employee",employeeService.findOneByUsername(username));
        }else{
            return null;
        }
        return "employee/employee";
    }

    @GetMapping("/add/employee")
    private String showFromAddEmployee(Model model){
        model.addAttribute("employee", new EmployeeDTO());
        model.addAttribute("offices", officeService.list());
        return "employee/new-employee";
    }

    @Override
    @PostMapping("/add/employee")
    public String add(@ModelAttribute EmployeeDTO employee, Model model) {
        employeeService.add(employee);
        return "redirect:/employees";
    }

    @Override
    @PostMapping("/update/employee") 
    public String update(@ModelAttribute EmployeeDTO employee, Model model) {
        employeeService.update(employee);
        return "redirect:/employee/"+employee.getUsername();
    }

    @Override
    @GetMapping("/delete/employee/{id}")
    public String delete(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        employeeService.delete(id);
        return "redirect:/employees";
    }

    @Override
    @GetMapping("/edit/employee/{id}")
    public String edit(@PathVariable("id") String stdId, Model model) {
        Long id = Long.parseLong(stdId);
        model.addAttribute("employee",employeeService.findOneById(id));
        return "employee/edit-employee";
    }
    
}

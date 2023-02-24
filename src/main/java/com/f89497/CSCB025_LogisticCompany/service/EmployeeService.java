package com.f89497.CSCB025_LogisticCompany.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f89497.CSCB025_LogisticCompany.dto.EmployeeDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Employee;
import com.f89497.CSCB025_LogisticCompany.entity.Office;
import com.f89497.CSCB025_LogisticCompany.entity.Role;
import com.f89497.CSCB025_LogisticCompany.entity.User;
import com.f89497.CSCB025_LogisticCompany.repository.EmployeeRepository;
import com.f89497.CSCB025_LogisticCompany.repository.OfficeRepository;
import com.f89497.CSCB025_LogisticCompany.repository.RoleRepository;
import com.f89497.CSCB025_LogisticCompany.repository.UserRepository;
import com.f89497.CSCB025_LogisticCompany.unity.Unity;

@Service
public class EmployeeService extends AbstractSevice<EmployeeDTO>{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public EmployeeDTO findOneById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        return Unity.mapEmployeeToEmployeeDTO(employee);
    }

    public EmployeeDTO findOneByUsername(String username){
        Employee employee = employeeRepository.findOneByUsername(username);
        return Unity.mapEmployeeToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> list() {
        return Unity.mapListEmployeeDTOs(employeeRepository.findAll());
    }

    @Override
    public void add(EmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = new User();
        user.setEnabled(true);
        user.setPassword("$2a$12$/2JuC//y4ViP2rsHDmY/pugPZ2CvDgZN2fxQbEgr2QTlQLukp3A5e");
        if(employeeDTO.getEmail() != null){
            user.setUsername(employeeDTO.getEmail());
            employeeDTO.setUsername(employeeDTO.getEmail());
        }else{
            user.setUsername(employeeDTO.getName());
            employeeDTO.setUsername(employeeDTO.getName());
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findOneByName("EMPLOYEE"));
        employeeRepository.save(mapper.map(employeeDTO,Employee.class));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        User user = userRepository.getUserByUsername(employee.getUsername());
        user.setEnabled(false);
        userRepository.save(user);
        employeeRepository.deleteById(id);
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();
        Employee oldEmployee = employeeRepository.findById(employeeDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + employeeDTO.getId()));
        oldEmployee = mapper.map(employeeDTO,Employee.class);
        Office office = officeRepository.findById(employeeDTO.getOfficeId()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + employeeDTO.getOfficeId()));
        oldEmployee.setOffice(office);
        employeeRepository.save(oldEmployee);
    }
    
}

package com.f89497.CSCB025_LogisticCompany.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f89497.CSCB025_LogisticCompany.dto.OfficeDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Company;
import com.f89497.CSCB025_LogisticCompany.entity.Office;
import com.f89497.CSCB025_LogisticCompany.repository.CompanyRepository;
import com.f89497.CSCB025_LogisticCompany.repository.EmployeeRepository;
import com.f89497.CSCB025_LogisticCompany.repository.OfficeRepository;
import com.f89497.CSCB025_LogisticCompany.repository.ShipmentRepository;
import com.f89497.CSCB025_LogisticCompany.unity.Unity;

@Service
public class OfficeService extends AbstractSevice<OfficeDTO>{

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public OfficeDTO findOneById(Long id) {
        Office office = officeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        ModelMapper mapper = new ModelMapper();
        return mapper.map(office,OfficeDTO.class);
    }

    @Override
    public List<OfficeDTO> list() {
        return Unity.mapListOfficeDTOs(officeRepository.findAll());
    }

    @Override
    public void add(OfficeDTO officeDTO) {
        ModelMapper mapper = new ModelMapper();
        Office office = new Office();
        office = mapper.map(officeDTO,Office.class);
        Company company = companyRepository.findById(officeDTO.getCompany_id()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + officeDTO.getCompany_id()));
        office.setCompany(company);
        officeRepository.save(office);
    }

    @Override
    public void delete(Long id) {
        Office office = officeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        office.getSendShipments().stream().forEach(e -> {
            if (e.getId() != null) 
                shipmentRepository.deleteById(e.getId());
        } );
        office.getReceiveShipments().stream().forEach(e -> {
            if (e.getId() != null) 
                shipmentRepository.deleteById(e.getId());
        } );
        office.getEmployees().stream().forEach(e -> {
            if (e.getId() != null) 
                employeeRepository.deleteById(e.getId());
        } );
        officeRepository.deleteById(id);
    }

    @Override
    public void update(OfficeDTO officeDTO) {
        ModelMapper mapper = new ModelMapper();
        Office oldOffice = officeRepository.findById(officeDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + officeDTO.getId()));
        oldOffice = mapper.map(oldOffice,Office.class);
        if (officeDTO.getCompany_id() != null){
            oldOffice.setCompany(companyRepository.findById(officeDTO.getCompany_id()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + officeDTO.getCompany_id())));
        }
        officeRepository.save(oldOffice);
    }
    
}

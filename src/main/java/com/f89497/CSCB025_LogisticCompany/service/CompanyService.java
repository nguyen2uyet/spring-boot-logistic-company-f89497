package com.f89497.CSCB025_LogisticCompany.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f89497.CSCB025_LogisticCompany.dto.CompanyDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Company;
import com.f89497.CSCB025_LogisticCompany.repository.CompanyRepository;
import com.f89497.CSCB025_LogisticCompany.unity.Unity;

@Service
public class CompanyService extends AbstractSevice<CompanyDTO>{

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyDTO findOneById(Long id) {
        ModelMapper mapper = new ModelMapper();
        Company company = companyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        return mapper.map(company,CompanyDTO.class);
    }

    @Override
    public List<CompanyDTO> list() {
        List<CompanyDTO> list = Unity.mapList(companyRepository.findAll(), CompanyDTO.class);
        return list;
    }

    @Override
    public void add(CompanyDTO companyDTO) {
        ModelMapper mapper = new ModelMapper();
        companyRepository.save(mapper.map(companyDTO,Company.class));
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void update(CompanyDTO companyDTO) {
        Company oldCompany = companyRepository.findById(companyDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + companyDTO.getId()));
        ModelMapper mapper = new ModelMapper();
        oldCompany = mapper.map(companyDTO, Company.class);
        companyRepository.save(oldCompany);
    }

    
}

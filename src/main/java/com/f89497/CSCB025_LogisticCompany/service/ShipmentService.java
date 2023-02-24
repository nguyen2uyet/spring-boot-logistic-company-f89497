package com.f89497.CSCB025_LogisticCompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f89497.CSCB025_LogisticCompany.dto.ShipmentDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Shipment;
import com.f89497.CSCB025_LogisticCompany.repository.ShipmentRepository;
import com.f89497.CSCB025_LogisticCompany.unity.Unity;

@Service
public class ShipmentService extends AbstractSevice<ShipmentDTO>{

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public ShipmentDTO findOneById(Long id) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + id));
        ShipmentDTO shipmentDTO = Unity.mapShipmentToShipmentDTO(shipment);
        return shipmentDTO;
    }

    @Override
    public List<ShipmentDTO> list() {
        return Unity.mapListShipmentDTOs(shipmentRepository.findAll());
    }

    @Override
    public void add(ShipmentDTO shipmentDTO) {
        Shipment shipment = new Shipment();
        Shipment shipment2 = Unity.mapShipmentDTOtoShipment(shipmentDTO,shipment);
        shipmentRepository.save(shipment2);
    }

    @Override
    public void delete(Long id) {
        shipmentRepository.deleteById(id);
    }

    @Override
    public void update(ShipmentDTO shipmentDTO) {
        Shipment oldShipment = shipmentRepository.findById(shipmentDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Ivalid id: " + shipmentDTO.getId()));
        oldShipment = Unity.mapShipmentDTOtoShipment(shipmentDTO,oldShipment);
        shipmentRepository.save(oldShipment);
    }
    
}

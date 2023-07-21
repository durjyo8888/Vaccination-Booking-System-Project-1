package com.example.VaccinationBookingSystem.Service;

import com.example.VaccinationBookingSystem.Dto.RequestDto.CenterRequestDto;
import com.example.VaccinationBookingSystem.Dto.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.Model.VaccinationCenter;
import com.example.VaccinationBookingSystem.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationCenterService {
    @Autowired
    VaccinationCenterRepository centerRepository;

    public CenterResponseDto addCenter(CenterRequestDto centerRequestDto) {
        // request dto -> entity
        VaccinationCenter center = new VaccinationCenter();

        center.setCenterName(centerRequestDto.getCenterName());
        center.setCenterType(centerRequestDto.getCenterType());
        center.setAddress(centerRequestDto.getAddress());

        // save entity to db
        VaccinationCenter savedCenter = centerRepository.save(center);

        // entity -> response Dto
        CenterResponseDto centerResponseDto = new CenterResponseDto();

        centerResponseDto.setCenterName(savedCenter.getCenterName());
        centerResponseDto.setAddress(savedCenter.getAddress());
        centerResponseDto.setCenterType(savedCenter.getCenterType());

        return centerResponseDto;
    }
}

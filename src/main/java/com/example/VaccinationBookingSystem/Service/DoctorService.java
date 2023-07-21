package com.example.VaccinationBookingSystem.Service;

import com.example.VaccinationBookingSystem.Dto.RequestDto.DoctorRequestDto;
import com.example.VaccinationBookingSystem.Dto.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.Dto.ResponseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.Exception.CenterNotFoundException;
import com.example.VaccinationBookingSystem.Model.Doctor;
import com.example.VaccinationBookingSystem.Model.VaccinationCenter;
import com.example.VaccinationBookingSystem.Repository.DoctorRepository;
import com.example.VaccinationBookingSystem.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    VaccinationCenterRepository centerRepository;

    @Autowired
    DoctorRepository doctorRepository;

    public DoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto) {

        Optional<VaccinationCenter> vaccinationCenterOptional = centerRepository.findById(doctorRequestDto.getCenterId());
        if(vaccinationCenterOptional.isEmpty()){
            throw new CenterNotFoundException("Sorry! Wrong center Id");
        }

        VaccinationCenter center = vaccinationCenterOptional.get();

        // create doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequestDto.getName());
        doctor.setAge(doctorRequestDto.getAge());
        doctor.setEmailId(doctorRequestDto.getEmailId());
        doctor.setGender(doctorRequestDto.getGender());
        doctor.setCenter(center);

        // add in center's doctor list
        center.getDoctors().add(doctor);

        VaccinationCenter savedCenter = centerRepository.save(center); // save both center and doctor

        // prepare response dto
        List<Doctor> doctors = savedCenter.getDoctors();
        Doctor latestSavedDoctor = doctors.get(doctors.size()-1);

        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterType(savedCenter.getCenterType());
        centerResponseDto.setAddress(savedCenter.getAddress());
        centerResponseDto.setCenterName(savedCenter.getCenterName());

        DoctorResponseDto doctorResponseDto = new DoctorResponseDto();
        doctorResponseDto.setName(latestSavedDoctor.getName());
        doctorResponseDto.setMessage("Congrats!! You have been registered!");
        doctorResponseDto.setCenterResponseDto(centerResponseDto);

        return doctorResponseDto;
    }

    public List<String> getByAgeGreaterThan(int age) {
        List<Doctor> doctors = doctorRepository.getByAgeGreaterThan(age);
        List<String> ans = new ArrayList<>();

        for(Doctor d: doctors)
            ans.add(d.getName());

        return ans;
    }
}

package com.example.VaccinationBookingSystem.Service;

import com.example.VaccinationBookingSystem.Dto.RequestDto.BookAppointmentRequestDto;
import com.example.VaccinationBookingSystem.Dto.ResponseDto.BookAppointmentResponseDto;
import com.example.VaccinationBookingSystem.Dto.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.Exception.DoctorNotFoundException;
import com.example.VaccinationBookingSystem.Exception.PersonNotFoundException;
import com.example.VaccinationBookingSystem.Model.Appointment;
import com.example.VaccinationBookingSystem.Model.Doctor;
import com.example.VaccinationBookingSystem.Model.Person;
import com.example.VaccinationBookingSystem.Model.VaccinationCenter;
import com.example.VaccinationBookingSystem.Repository.AppointmentRepository;
import com.example.VaccinationBookingSystem.Repository.DoctorRepository;
import com.example.VaccinationBookingSystem.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;
import java.util.UUID;

public class AppointmentService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    JavaMailSender javaMailSender;
    public BookAppointmentResponseDto bookAppointment(BookAppointmentRequestDto bookAppointmentRequestDto) {

        Optional<Person> optionalPerson = personRepository.findById(bookAppointmentRequestDto.getPersonId());
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoundException("Invalid personId");
        }

        Optional<Doctor> optionalDoctor = doctorRepository.findById(bookAppointmentRequestDto.getDoctorId());
        if(optionalDoctor.isEmpty()){
            throw new DoctorNotFoundException("Invalid DoctorId");
        }

        Person person = optionalPerson.get();
        Doctor doctor = optionalDoctor.get();

        // create an appointment object
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(String.valueOf(UUID.randomUUID()));
        appointment.setPerson(person);
        appointment.setDoctor(doctor);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        doctor.getAppointments().add(savedAppointment);
        person.getAppointments().add(savedAppointment);

        Doctor savedDoctor = doctorRepository.save(doctor);
        Person savedPerson = personRepository.save(person);
        VaccinationCenter center = savedDoctor.getCenter();

        // send an email
        String text = "Congrats!! "+savedPerson.getName()+" Your appointment has been booked with Doctor "+
                savedDoctor.getName() + ". Your vaccination center name is: " + center.getCenterName() + " Please reach at this address "+
                center.getAddress() + " at this time: " + savedAppointment.getAppointmentDate()+" Dhanyawad!!!";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("acciojobspring888@gmail.com");
        simpleMailMessage.setTo(savedPerson.getEmailId());
        simpleMailMessage.setSubject("Congrats!! Appointment Done!!");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

        // prepare the response dto;

        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterName(center.getCenterName());
        centerResponseDto.setAddress(center.getAddress());
        centerResponseDto.setCenterType(center.getCenterType());

        BookAppointmentResponseDto bookAppointmentResponseDto = new BookAppointmentResponseDto();
        bookAppointmentResponseDto.setPersonName(savedPerson.getName());
        bookAppointmentResponseDto.setDoctorName(savedDoctor.getName());
        bookAppointmentResponseDto.setAppointmentId(savedAppointment.getAppointmentId());
        bookAppointmentResponseDto.setAppointmentDate(savedAppointment.getAppointmentDate());
        bookAppointmentResponseDto.setCenterResponseDto(centerResponseDto);

        return bookAppointmentResponseDto;

    }
}

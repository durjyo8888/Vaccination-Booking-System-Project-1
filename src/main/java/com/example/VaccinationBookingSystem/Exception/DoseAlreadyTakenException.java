package com.example.VaccinationBookingSystem.Exception;

public class DoseAlreadyTakenException extends RuntimeException{
    public DoseAlreadyTakenException(String message){
        super(message);
    }
}

package com.example.VaccinationBookingSystem.Exception;

public class CenterNotFoundException extends RuntimeException{
    public CenterNotFoundException(String message){
        super(message);
    }
}

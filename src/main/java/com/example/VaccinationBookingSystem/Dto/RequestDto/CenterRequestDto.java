package com.example.VaccinationBookingSystem.Dto.RequestDto;

import com.example.VaccinationBookingSystem.Enum.CenterType;
import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CenterRequestDto {
    String centerName;
    CenterType centerType;
    String address;
}

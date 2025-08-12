package com.drivz.bookingService.dtos;

import com.drivz.commonLibrary.models.ExactLocation;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequestDto {
    
    private long passengerId;
    
    private ExactLocation startLocation;
    
    private ExactLocation endLocation;
}

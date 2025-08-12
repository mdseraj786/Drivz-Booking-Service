package com.drivz.bookingService.dtos;

import com.drivz.commonLibrary.models.Driver;
import lombok.*;

import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingResponseDto {
    private long bookingId;
    
    private String bookingStatus;
    
    private Optional<Driver> driver;
}

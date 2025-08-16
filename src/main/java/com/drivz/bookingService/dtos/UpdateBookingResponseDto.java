package com.drivz.bookingService.dtos;

import com.drivz.commonLibrary.models.BookingStatus;
import com.drivz.commonLibrary.models.Driver;
import lombok.*;

import java.util.Optional;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingResponseDto {
    private Long bookingId;
    
    private BookingStatus status;
    
    private Optional<Driver> driver;
}

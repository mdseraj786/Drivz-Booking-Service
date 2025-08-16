package com.drivz.bookingService.dtos;

import com.drivz.commonLibrary.models.BookingStatus;
import com.drivz.commonLibrary.models.Driver;
import lombok.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDto {
    private BookingStatus status;
    private Long driverId;  
}

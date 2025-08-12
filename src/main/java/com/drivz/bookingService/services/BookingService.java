package com.drivz.bookingService.services;

import com.drivz.bookingService.dtos.CreateBookingRequestDto;
import com.drivz.bookingService.dtos.CreateBookingResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    CreateBookingResponseDto createBooking(CreateBookingRequestDto createBookingDto);
}

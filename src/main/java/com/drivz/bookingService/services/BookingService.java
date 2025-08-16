package com.drivz.bookingService.services;

import com.drivz.bookingService.dtos.CreateBookingRequestDto;
import com.drivz.bookingService.dtos.CreateBookingResponseDto;
import com.drivz.bookingService.dtos.UpdateBookingRequestDto;
import com.drivz.bookingService.dtos.UpdateBookingResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    CreateBookingResponseDto createBooking(CreateBookingRequestDto createBookingDto);

    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto requestDto, long bookingId);
}

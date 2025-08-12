package com.drivz.bookingService.controllers;

import com.drivz.bookingService.dtos.CreateBookingRequestDto;
import com.drivz.bookingService.dtos.CreateBookingResponseDto;
import com.drivz.bookingService.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingRequestDto createBookingDto){
        
        CreateBookingResponseDto response = bookingService.createBooking(createBookingDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

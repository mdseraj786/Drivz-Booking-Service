package com.drivz.bookingService.controllers;

import com.drivz.bookingService.dtos.CreateBookingRequestDto;
import com.drivz.bookingService.dtos.CreateBookingResponseDto;
import com.drivz.bookingService.dtos.UpdateBookingRequestDto;
import com.drivz.bookingService.dtos.UpdateBookingResponseDto;
import com.drivz.bookingService.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @PostMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@PathVariable("bookingId") long bookingId, @RequestBody UpdateBookingRequestDto requestDto ){
        System.out.println(bookingId);
        System.out.println(requestDto);
        return new ResponseEntity<>(bookingService.updateBooking(requestDto,bookingId),HttpStatus.OK);
    }
}

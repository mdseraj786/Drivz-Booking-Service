package com.drivz.bookingService.services;

import com.drivz.bookingService.dtos.CreateBookingRequestDto;
import com.drivz.bookingService.dtos.CreateBookingResponseDto;
import com.drivz.bookingService.dtos.DriverLocationDto;
import com.drivz.bookingService.dtos.NearbyDriverRequestDto;
import com.drivz.bookingService.repositories.BookingRepository;
import com.drivz.bookingService.repositories.PassengerRepository;
import com.drivz.commonLibrary.models.Booking;
import com.drivz.commonLibrary.models.BookingStatus;
import com.drivz.commonLibrary.models.Driver;
import com.drivz.commonLibrary.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{
    private static final String LOCATION_SERVICE = "http://localhost:8484";

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, RestTemplate restTemplate) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingRequestDto createBookingDto) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(createBookingDto.getPassengerId());

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(createBookingDto.getStartLocation())
                .endLocation(createBookingDto.getEndLocation())
//                .passenger(optionalPassenger.get())
                .build();

        NearbyDriverRequestDto request = NearbyDriverRequestDto.builder()
                .latitude(createBookingDto.getStartLocation().getLatitude())
                .longitude(createBookingDto.getStartLocation().getLongitude())
                .build();

        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request, DriverLocationDto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            List<DriverLocationDto> driversLocations = List.of(result.getBody());
            driversLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId()+" lat: "+ driverLocationDto.getLatitude()+ " long: "+ driverLocationDto.getLongitude());
            });
        }
        Booking saveBooking = bookingRepository.save(booking);
        return CreateBookingResponseDto.builder()
                .bookingId(saveBooking.getId())
                .bookingStatus(saveBooking.getBookingStatus().toString())
//                .driver(Optional.of(saveBooking.getDriver()))
                
                .build();
    }
}

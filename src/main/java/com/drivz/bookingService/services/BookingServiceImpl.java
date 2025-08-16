package com.drivz.bookingService.services;

import com.drivz.bookingService.api.DrivzSocketApi;
import com.drivz.bookingService.api.LocationServiceApi;
import com.drivz.bookingService.dtos.*;
import com.drivz.bookingService.repositories.BookingRepository;
import com.drivz.bookingService.repositories.DriverRepository;
import com.drivz.bookingService.repositories.PassengerRepository;
import com.drivz.commonLibrary.models.Booking;
import com.drivz.commonLibrary.models.BookingStatus;
import com.drivz.commonLibrary.models.Driver;
import com.drivz.commonLibrary.models.Passenger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
//    private static final String LOCATION_SERVICE = "http://localhost:8484";

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;
    private final DrivzSocketApi drivzSocketApi;


    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, RestTemplate restTemplate, LocationServiceApi locationServiceApi,
                              DriverRepository driverRepository, DrivzSocketApi drivzSocketApi) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
        this.locationServiceApi = locationServiceApi;
        this.driverRepository = driverRepository;
        this.drivzSocketApi = drivzSocketApi;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingRequestDto createBookingDto) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(createBookingDto.getPassengerId());

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(createBookingDto.getStartLocation())
                .endLocation(createBookingDto.getEndLocation())
                .passenger(optionalPassenger.get())
                .build();
        Booking saveBooking = bookingRepository.save(booking);

        NearbyDriverRequestDto request = NearbyDriverRequestDto.builder()
                .latitude(createBookingDto.getStartLocation().getLatitude())
                .longitude(createBookingDto.getStartLocation().getLongitude())
                .build();
// here use rest template for communication 
//        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request, DriverLocationDto[].class);
//
//        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
//            List<DriverLocationDto> driversLocations = List.of(result.getBody());
//            driversLocations.forEach(driverLocationDto -> {
//                System.out.println(driverLocationDto.getDriverId()+" lat: "+ driverLocationDto.getLatitude()+ " long: "+ driverLocationDto.getLongitude());
//            });
//        }

        processNearbyDriversAsync(request, createBookingDto.getPassengerId(), saveBooking.getId());

        return CreateBookingResponseDto.builder()
                .bookingId(saveBooking.getId())
                .bookingStatus(saveBooking.getBookingStatus().toString())
//                .driver(Optional.of(saveBooking.getDriver()))
                .build();
    }

    private void processNearbyDriversAsync(NearbyDriverRequestDto requestDto, long passengerId, Long bookingId) {
        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);

        // how can we make async call

        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
                System.out.println(call + " " + response);
                // sleep for testing -- asynchronous
                 /*   try { 
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
                System.out.println(Arrays.toString(response.body()) + " " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<DriverLocationDto> driversLocations = List.of(response.body());
                    driversLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " lat: " + driverLocationDto.getLatitude() + " long: " + driverLocationDto.getLongitude());
                    });
                    raiseRiderRequestAsync(new RideRequestDto().builder().passengerId(passengerId).bookingId(bookingId).build());
                } else {
                    System.out.println("request failed " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    
    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto requestDto, long bookingId) {

        Optional<Driver> driver = driverRepository.findById(requestDto.getDriverId());

        bookingRepository.updateBookingById(bookingId, requestDto.getStatus(), driver.get());

        Optional<Booking> booking = bookingRepository.findById(bookingId);

        return UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .status(booking.get().getBookingStatus())
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .build();
    }

    private void raiseRiderRequestAsync(RideRequestDto requestDto) {
        Call<Boolean> call = drivzSocketApi.raiseRideRequest(requestDto);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Boolean result = response.body();
                    System.out.println("Driver response is : " + result);
                } else {
                    System.out.println("request failed for raise request    " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

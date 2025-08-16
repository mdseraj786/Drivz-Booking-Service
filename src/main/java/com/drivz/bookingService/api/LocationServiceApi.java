package com.drivz.bookingService.api;

import com.drivz.bookingService.dtos.DriverLocationDto;
import com.drivz.bookingService.dtos.NearbyDriverRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LocationServiceApi {
    
    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearbyDrivers(@Body NearbyDriverRequestDto nearbyDriverRequestDto);
}

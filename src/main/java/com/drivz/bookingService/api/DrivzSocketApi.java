package com.drivz.bookingService.api;

import com.drivz.bookingService.dtos.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DrivzSocketApi {

    @POST("/api/socket/newride")
    Call<Boolean> raiseRideRequest(@Body RideRequestDto requestDto);
}

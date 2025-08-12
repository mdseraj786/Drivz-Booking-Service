package com.drivz.bookingService.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationDto {
    private String driverId;
    private double longitude;
    private double latitude;

    
}

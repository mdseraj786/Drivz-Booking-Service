package com.drivz.bookingService.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NearbyDriverRequestDto {
    
    private double longitude;
    private double latitude;

   
}

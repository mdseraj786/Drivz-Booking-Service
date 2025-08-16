package com.drivz.bookingService.repositories;

import com.drivz.commonLibrary.models.Booking;
import com.drivz.commonLibrary.models.BookingStatus;
import com.drivz.commonLibrary.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface DriverRepository extends JpaRepository<Driver,Long> {
    
 
}

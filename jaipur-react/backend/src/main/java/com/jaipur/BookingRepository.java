package com.jaipur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserEmail(String userEmail);
    List<Booking> findByUserEmailIgnoreCase(String userEmail);

    @Query(value = "SELECT * FROM bookings WHERE travel_date LIKE CONCAT(:travelDate, '%')", nativeQuery = true)
    List<Booking> findByTravelDate(@Param("travelDate") String travelDate);
}

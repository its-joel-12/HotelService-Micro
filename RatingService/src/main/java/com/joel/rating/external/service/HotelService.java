package com.joel.rating.external.service;

import com.joel.rating.model.Hotel;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "hotel-service")
public interface HotelService {
    @GetMapping("hotels/{hotelId}")
    ResponseEntity<Hotel> getHotel(@PathVariable String hotelId);

    @GetMapping("hotels/exists/{hotelId}")
    ResponseEntity<Boolean> doesHotelExist(@PathVariable String hotelId);


}

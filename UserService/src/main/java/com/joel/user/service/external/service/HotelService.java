package com.joel.user.service.external.service;

import com.joel.user.service.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service")
//@Service
public interface HotelService {

    @GetMapping("hotels/{hotelId}")
    ResponseEntity<Hotel> getHotel(@PathVariable String hotelId);

    @GetMapping("hotels/exists/{hotelId}")
    ResponseEntity<Boolean> doesHotelExist(@PathVariable String hotelId);
}

package com.joel.hotel.controller;

import com.joel.hotel.dto.ApiResponse;
import com.joel.hotel.dto.HotelDto;
import com.joel.hotel.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotels")
@CrossOrigin
public class HotelController {
    @Autowired
    private HotelService hotelService;

    // CREATE HOTEL
    @PostMapping()
    public ResponseEntity<HotelDto> createHotel(@Valid @RequestBody HotelDto hotelDto) {
        return new ResponseEntity<>(hotelService.createHotel(hotelDto), HttpStatus.CREATED);
    }

    // GET ALL HOTELS
    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        return new ResponseEntity<>(hotelService.getAllHotels(pageNumber, pageSize), HttpStatus.OK);
    }

    // GET HOTEL BY ID
    @GetMapping("{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable String hotelId) {
        return new ResponseEntity<>(hotelService.getHotelById(hotelId), HttpStatus.OK);
    }

    // UPDATE HOTEL BY ID
    @PutMapping("{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable String hotelId,@Valid @RequestBody HotelDto hotelDto) {
        return new ResponseEntity<>(hotelService.updateHotelById(hotelId, hotelDto), HttpStatus.OK);
    }

    // DELETE HOTEL BY ID
    @DeleteMapping("{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotelById(@PathVariable String hotelId) {
        hotelService.deleteHotelById(hotelId);
        return new ResponseEntity<>(new ApiResponse("Hotel deleted successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

    // DOES RATING EXISTS
    @GetMapping("exists/{hotelId}")
    public ResponseEntity<Boolean> doesRatingExist(@PathVariable String hotelId){
        return new ResponseEntity<>(hotelService.doesHotelExist(hotelId), HttpStatus.OK);
    }
}

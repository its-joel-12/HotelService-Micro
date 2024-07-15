package com.joel.rating.controller;

import com.joel.rating.dto.ApiResponse;
import com.joel.rating.dto.RatingDto;
import com.joel.rating.model.Rating;
import com.joel.rating.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    // CREATE RATING
    @PostMapping
    public ResponseEntity<RatingDto> createRating(@Valid @RequestBody RatingDto ratingDto) {
        return new ResponseEntity<>(ratingService.createRating(ratingDto), HttpStatus.CREATED);
    }

    // GET ALL RATINGS
    @GetMapping
    public ResponseEntity<List<RatingDto>> getAllRatings(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize
    ) {
        return new ResponseEntity<>(ratingService.getAllRatings(pageNumber, pageSize), HttpStatus.OK);
    }

    // GET RATINGS BY USER ID
    @GetMapping("users/{userId}")
    public ResponseEntity<List<RatingDto>> getRatingsByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(ratingService.getRatingsByUserId(userId), HttpStatus.OK);
    }

    // GET RATINGS BY HOTEL ID
    @GetMapping("hotels/{hotelId}")
    public ResponseEntity<List<RatingDto>> getRatingsByHotelId(@PathVariable String hotelId) {
        return new ResponseEntity<>(ratingService.getRatingsByHotelId(hotelId), HttpStatus.OK);
    }

    // UPDATE RATING BY HOTEL ID
    @PutMapping("{ratingId}")
    public ResponseEntity<RatingDto> updateRatingById(@PathVariable String ratingId, @Valid @RequestBody RatingDto ratingDto) {
        return new ResponseEntity<>(ratingService.updateRatingById(ratingId, ratingDto), HttpStatus.OK);
    }

    // DELETE RATING BY ID
    @DeleteMapping("{ratingId}")
    public ResponseEntity<ApiResponse> deleteRatingById(@PathVariable String ratingId) {
        ratingService.deleteRatingById(ratingId);
        return new ResponseEntity<>(new ApiResponse("Rating deleted successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

    // DOES RATING EXISTS
    @GetMapping("exists/{ratingId}")
    public ResponseEntity<Boolean> doesRatingExist(@PathVariable String ratingId){
        return new ResponseEntity<>(ratingService.doesRatingExist(ratingId), HttpStatus.OK);
    }

}

package com.joel.user.service.external.service;

import com.joel.user.service.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "rating-service")
public interface RatingService {

    @GetMapping("ratings/users/{userId}")
    ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId);

    @PostMapping("ratings")
    ResponseEntity<Rating> createRating(Rating rating);

    @PutMapping("ratings/{ratingId}")
    ResponseEntity<Rating> updateRating(@PathVariable String ratingId, Rating rating);

    @DeleteMapping("ratings/{ratingId}")
    ResponseEntity<Void> deleteRating(@PathVariable String ratingId);
}

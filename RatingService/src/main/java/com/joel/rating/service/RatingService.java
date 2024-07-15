package com.joel.rating.service;

import com.joel.rating.dto.RatingDto;
import com.joel.rating.model.Rating;

import java.util.List;

public interface RatingService {
    RatingDto createRating(RatingDto ratingDto);
    List<RatingDto> getAllRatings(Integer pageNumber, Integer pageSize);
    List<RatingDto> getRatingsByUserId(String userId);
    List<RatingDto> getRatingsByHotelId(String hotelId);
    RatingDto updateRatingById(String ratingId, RatingDto ratingDto);
    void deleteRatingById(String ratingId);
    Boolean doesRatingExist(String ratingId);
}

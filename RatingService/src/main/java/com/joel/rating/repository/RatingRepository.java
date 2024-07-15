package com.joel.rating.repository;

import com.joel.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {
    // custom finder methods
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);

}

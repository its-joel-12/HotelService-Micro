package com.joel.rating.repository;

import com.joel.rating.model.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepo;

    Rating rating1;

    @BeforeEach
    void setUp() {
        rating1 = new Rating("id1", "userid1", "hotelid1", 5, "feedback1");
        ratingRepo.save(rating1);
    }

    @AfterEach
    void tearDown() {
        rating1 = null;
        ratingRepo.deleteAll();
    }

    @Test
    void findByUserId() {
        List<Rating> ratingList = ratingRepo.findByUserId("userid1");
        assertThat(ratingList.get(0).getFeedback()).isEqualTo(rating1.getFeedback());
    }

    @Test
    void findByHotelId() {
        List<Rating> ratingList = ratingRepo.findByHotelId("hotelid1");
        assertThat(ratingList.get(0).getFeedback()).isEqualTo(rating1.getFeedback());
    }
}
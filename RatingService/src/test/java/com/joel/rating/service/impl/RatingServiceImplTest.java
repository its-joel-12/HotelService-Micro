package com.joel.rating.service.impl;

import com.joel.rating.dto.RatingDto;
import com.joel.rating.model.Rating;
import com.joel.rating.repository.RatingRepository;
import com.joel.rating.service.RatingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class RatingServiceImplTest {

    AutoCloseable autoCloseable;

    @Mock
    private RatingRepository ratingRepo;

    @Mock
    private ModelMapper modelMapper;

    private RatingService ratingService;

    Rating rating1;
    Rating rating2;
    RatingDto ratingDto1;
    RatingDto ratingDto2;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        ratingService = new RatingServiceImpl(ratingRepo, modelMapper);

        rating1 = new Rating("id1", "userid1", "hotelid1", 5, "feedback1");
        ratingDto1 = new RatingDto("id1", "userid1", "hotelid1", 5, "feedback1");
        rating2 = new Rating("id2", "userid2", "hotelid2", 10, "feedback2");
        ratingDto2 = new RatingDto("id2", "userid2", "hotelid2", 10, "feedback2");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @Disabled
    void createRating() {
    }

    @Test
    @Disabled
    void getAllRatings() {
    }

    @Test
    @Disabled
    void getRatingsByUserId() {
    }

    @Test
    @Disabled
    void getRatingsByHotelId() {
    }

    @Test
    @Disabled
    void updateRatingById() {
    }

    @Test
    @Disabled
    void deleteRatingById() {
    }

    @Test
    @Disabled
    void doesRatingExist() {
    }
}
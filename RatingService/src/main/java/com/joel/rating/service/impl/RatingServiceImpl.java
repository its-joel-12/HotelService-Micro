package com.joel.rating.service.impl;

import com.joel.rating.dto.RatingDto;
import com.joel.rating.exception.ResourceNotFoundException;
import com.joel.rating.external.service.HotelService;
import com.joel.rating.external.service.UserService;
import com.joel.rating.model.Hotel;
import com.joel.rating.model.Rating;
import com.joel.rating.repository.RatingRepository;
import com.joel.rating.service.RatingService;
//import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.joel.rating.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserService userService;

    public RatingServiceImpl(RatingRepository ratingRepo, ModelMapper modelMapper) {
        this.ratingRepo = ratingRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public RatingDto createRating(RatingDto ratingDto) {

        if (hotelService.doesHotelExist(ratingDto.getHotelId()).getBody()) {

            if (userService.doesUserExist(ratingDto.getUserId()).getBody()) {
                Rating rating = modelMapper.map(ratingDto, Rating.class);
                String randomRatingId = UUID.randomUUID().toString();
                rating.setRatingId(randomRatingId);
                Rating savedRating = ratingRepo.save(rating);
                return modelMapper.map(savedRating, RatingDto.class);
            } else {
                throw new ResourceNotFoundException("User with the given id is not found: " + ratingDto.getUserId());
            }

        } else {
            throw new ResourceNotFoundException("Hotel with the given id is not found: " + ratingDto.getHotelId());
        }

    }

    @Override
    public List<RatingDto> getAllRatings(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Rating> allPageRatings = ratingRepo.findAll(pageable);
        List<Rating> allRatings = allPageRatings.getContent();

        List<RatingDto> allRatingDtos = allRatings.stream().map(r -> modelMapper.map(r, RatingDto.class)).collect(Collectors.toList());
        return allRatingDtos;
    }

    @Override
    public List<RatingDto> getRatingsByUserId(String userId) {
        List<Rating> ratingsByUserId = ratingRepo.findByUserId(userId);

        List<RatingDto> ratingDtos = ratingsByUserId.stream().map(r -> modelMapper.map(r, RatingDto.class)).collect(Collectors.toList());
        return ratingDtos;
    }

    @Override
    public List<RatingDto> getRatingsByHotelId(String hotelId) {
        List<Rating> ratingsByHotelId = ratingRepo.findByHotelId(hotelId);

        List<RatingDto> ratingDtos = ratingsByHotelId.stream().map(r -> modelMapper.map(r, RatingDto.class)).collect(Collectors.toList());
        return ratingDtos;
    }

    @Transactional
    @Override
    public RatingDto updateRatingById(String ratingId, RatingDto ratingDto) {

        if (hotelService.doesHotelExist(ratingDto.getHotelId()).getBody()) {

            if (userService.doesUserExist(ratingDto.getUserId()).getBody()) {
                Rating rating1 = ratingRepo.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("Rating not found with the given id: " + ratingId));
                rating1.setRatingId(ratingId);
                rating1.setRating(ratingDto.getRating());
                rating1.setFeedback(ratingDto.getFeedback());
                Rating updatedRating = ratingRepo.save(rating1);
                return modelMapper.map(updatedRating, RatingDto.class);
            } else {
                throw new ResourceNotFoundException("User with the given id is not found: " + ratingDto.getUserId());
            }

        } else {
            throw new ResourceNotFoundException("Hotel with the given id is not found: " + ratingDto.getHotelId());
        }
    }

    @Transactional
    @Override
    public void deleteRatingById(String ratingId) {
        Rating rating1 = ratingRepo.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("Rating not found with the given id: " + ratingId));
        ratingRepo.delete(rating1);
    }

    @Override
    public Boolean doesRatingExist(String ratingId) {
        return ratingRepo.existsById(ratingId);
    }
}

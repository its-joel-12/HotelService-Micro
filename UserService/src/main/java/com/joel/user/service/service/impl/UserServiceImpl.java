package com.joel.user.service.service.impl;

//import com.joel.user.service.dto.RatingDto;
import com.joel.user.service.dto.UserDto;
import com.joel.user.service.dto.UserRatingDto;
import com.joel.user.service.exception.ResourceNotFoundException;
import com.joel.user.service.external.service.HotelService;
import com.joel.user.service.external.service.RatingService;
import com.joel.user.service.model.Hotel;
import com.joel.user.service.model.Rating;
import com.joel.user.service.model.User;
import com.joel.user.service.repository.UserRepository;
import com.joel.user.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
//import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) {
        String randomUserId = UUID.randomUUID().toString();

        User user = modelMapper.map(userDto, User.class);
        user.setUserId(randomUserId);

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers(Integer pageNumber, Integer pageSize) {
        // implement RATING SERVICE API CALL
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> allPageUsers = userRepo.findAll(pageable);
        List<User> allRandomUsers = allPageUsers.getContent();

        List<UserDto> allUsers = new ArrayList<>();
        allRandomUsers.forEach(u -> {
            allUsers.add(this.getUserById(u.getUserId()));
        });


//        List<User> allUsers = userRepo.findAll();
/*

        allUsers = allUsers.stream().map(user -> {

            user.setRatings(ratingService.getRatingsByUserId(user.getUserId()).getBody());
            return user;
        }).collect(Collectors.toList());
*/

        return allUsers;


    }

    @Override
    public UserDto getUserById(String userId) {
        // get user from database using repository

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with the given id is not found: " + userId));


        ResponseEntity<List<Rating>> ratingsByUserId = ratingService.getRatingsByUserId(user.getUserId());

        List<Rating> ratingList = ratingsByUserId.getBody().stream().
                map(r -> {
                    ResponseEntity<Hotel> hotel = hotelService.getHotel(r.getHotelId());
                    r.setHotel(hotel.getBody());
                    return r;
                }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto updateUserById(String userId, UserDto userDto) {
        User user1 = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with the given id: " + userId));
        user1.setUserId(userId);
        user1.setUserName(userDto.getUserName());
        user1.setUserEmail(userDto.getUserEmail());
        user1.setUserAbout(userDto.getUserAbout());
        User updatedUser = userRepo.save(user1);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Transactional
    @Override
    public void deleteUserById(String userId) {
        User user1 = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with the given id: " + userId));
        userRepo.delete(user1);
    }

    @Override
    public Boolean doesUserExist(String userId) {
        return userRepo.existsById(userId);
    }

    @Override
    public UserRatingDto createUserFeedback(UserRatingDto userRatingDto) {

        if (hotelService.doesHotelExist(userRatingDto.getHotelId()).getBody()) {
            String randomUserId = UUID.randomUUID().toString();

            User user = new User();
            user.setUserId(randomUserId);
            user.setUserName(userRatingDto.getUserName());
            user.setUserEmail(userRatingDto.getUserEmail());
            user.setUserAbout(userRatingDto.getUserAbout());

            User savedUser = userRepo.save(user);

            userRatingDto.setUserId(savedUser.getUserId());

//            String randomRatingId = UUID.randomUUID().toString();

            Rating rating = new Rating();
            rating.setRating(userRatingDto.getRating());
            rating.setFeedback(userRatingDto.getFeedback());
            rating.setUserId(userRatingDto.getUserId());
            rating.setHotelId(userRatingDto.getHotelId());

            ResponseEntity<Rating> savedRating = ratingService.createRating(rating);
            userRatingDto.setRatingId(savedRating.getBody().getRatingId());
            return userRatingDto;


        } else {
            throw new ResourceNotFoundException("Hotel with the given id is not found: " + userRatingDto.getHotelId());
        }
    }
}

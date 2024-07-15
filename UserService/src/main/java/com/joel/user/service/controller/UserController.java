package com.joel.user.service.controller;

import com.joel.user.service.dto.ApiResponse;
import com.joel.user.service.dto.UserDto;
import com.joel.user.service.dto.UserRatingDto;
import com.joel.user.service.exception.ResourceNotFoundException;
import com.joel.user.service.exception.UserServiceException;
import com.joel.user.service.model.User;
import com.joel.user.service.service.UserService;
import com.joel.user.service.service.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // CREATE USER
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
    }

    // CREATE USER FEEDBACK
    @PostMapping("user-feedback")
    public ResponseEntity<UserRatingDto> createUserFeedback(@Valid @RequestBody UserRatingDto userRatingDto) {
        return new ResponseEntity<>(userService.createUserFeedback(userRatingDto), HttpStatus.CREATED);
    }

    // GET ALL USERS
    @GetMapping
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelAllUsersFallback")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize
    ) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize), HttpStatus.OK);
    }

    // GET USER BY ID
    @GetMapping("{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Object> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    // UPDATE USER BY ID
    @PutMapping("{userId}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable String userId, @Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUserById(userId, userDto), HttpStatus.OK);
    }

    // DELETE USER BY ID
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

    // DOES RATING EXISTS
    @GetMapping("exists/{userId}")
    public ResponseEntity<Boolean> doesUserExist(@PathVariable String userId) {
        return new ResponseEntity<>(userService.doesUserExist(userId), HttpStatus.OK);
    }

    // ----------------------------------------------------------------------------

    // FALLBACK METHOD FOR CIRCUIT-BREAKER - ratingHotelFallback
    public ResponseEntity<UserDto> ratingHotelFallback(String userId, Exception ex) {
//        logger.info("Fallback is executed because service is down: " + ex.getMessage());

        UserDto userDto = new UserDto("dummy_user_id", "dummy_userName", "dummy_userEmail", "This dummy user is created because " + ex.getMessage().toUpperCase(), null);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // FALLBACK METHOD FOR CIRCUIT-BREAKER - ratingHotelAllUsersFallback
    public ResponseEntity<List<UserDto>> ratingHotelAllUsersFallback(Exception ex) {
        List<UserDto> userList = new ArrayList<>();
        userList.add(new UserDto("dummy_user_id", "dummy_userName", "dummy_userEmail", "This dummy user is created because " + ex.getMessage().toUpperCase(), null));
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}

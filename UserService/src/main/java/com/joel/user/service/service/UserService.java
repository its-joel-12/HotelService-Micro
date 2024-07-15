package com.joel.user.service.service;

//import com.joel.user.service.dto.RatingDto;
import com.joel.user.service.dto.UserDto;
import com.joel.user.service.dto.UserRatingDto;
import com.joel.user.service.model.User;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    List<UserDto> getAllUsers(Integer pageNumber, Integer pageSize);
    UserDto getUserById(String userId);
    UserDto updateUserById(String userId, UserDto userDto);
    void deleteUserById(String userId);
    Boolean doesUserExist(String userId);
    UserRatingDto createUserFeedback(UserRatingDto ratingDto);
}

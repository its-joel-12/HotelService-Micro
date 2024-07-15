package com.joel.user.service.dto;

import com.joel.user.service.model.Hotel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRatingDto {
    private String userId;

    @NotEmpty
    @NotBlank
    private String userName;

    @NotEmpty
    @NotBlank
    @Email
    private String userEmail;

    @NotEmpty
    @NotBlank
    private String userAbout;


    private String ratingId;

//    @NotEmpty
//    @NotBlank
//    private String userId;

    @NotEmpty
    @NotBlank
    private String hotelId;

    @NotNull
    @Min(1) // assuming rating should be at least 1
    @Max(10) // assuming rating should be at most 10
    private Integer rating;

    @NotEmpty
    @NotBlank
    private String feedback;

//    private Hotel hotel;
}

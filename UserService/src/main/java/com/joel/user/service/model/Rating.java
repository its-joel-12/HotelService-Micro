package com.joel.user.service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private String ratingId;
    private String userId;
    private String hotelId;
    private Integer rating;
    private String feedback;
    private Hotel hotel;
}

package com.joel.rating.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private String ratingId;

    @NotEmpty
    @NotBlank
    private String userId;

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
}

package com.joel.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {
    private String hotelId;

    @NotEmpty
    @NotBlank
    private String hotelName;

    @NotEmpty
    @NotBlank
    private String hotelLocation;

    @NotEmpty
    @NotBlank
    private String hotelAbout;
}

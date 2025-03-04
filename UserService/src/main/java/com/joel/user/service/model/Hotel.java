package com.joel.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    private String hotelId;
    private String hotelName;
    private String hotelLocation;
    private String hotelAbout;
}

package com.joel.hotel.service;

import com.joel.hotel.dto.HotelDto;
import com.joel.hotel.model.Hotel;

import java.util.List;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);
    List<HotelDto> getAllHotels(Integer pageNumber, Integer pageSize);
    HotelDto getHotelById(String hotelId);
    HotelDto updateHotelById(String hotelId, HotelDto hotelDto);
    void deleteHotelById(String hotelId);
    Boolean doesHotelExist(String hotelId);
}

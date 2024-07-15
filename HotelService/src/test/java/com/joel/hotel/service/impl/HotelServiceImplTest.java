package com.joel.hotel.service.impl;

import com.joel.hotel.dto.HotelDto;
import com.joel.hotel.model.Hotel;
import com.joel.hotel.repository.HotelRepository;
import com.joel.hotel.service.HotelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

    AutoCloseable autoCloseable;

    @Mock
    private HotelRepository hotelRepo;

    @Mock
    private ModelMapper modelMapper;

    private HotelService hotelService;

    Hotel hotel1;
    Hotel hotel2;
    HotelDto hotelDto1;
    HotelDto hotelDto2;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        hotelService = new HotelServiceImpl(hotelRepo, modelMapper);

        hotel1 = new Hotel("id1", "Hotel name 1", "Hotel location 1", "Hotel about 1");
        hotel2 = new Hotel("id2", "Hotel name 2", "Hotel location 2", "Hotel about 2");
        hotelDto1 = new HotelDto("id1", "Hotel name 1", "Hotel location 1", "Hotel about 1");
        hotelDto2 = new HotelDto("id2", "Hotel name 2", "Hotel location 2", "Hotel about 2");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createHotel() {
        when(modelMapper.map(hotelDto1, Hotel.class)).thenReturn(hotel1);
        when(hotelRepo.save(any(Hotel.class))).thenReturn(hotel1);
        when(modelMapper.map(hotel1, HotelDto.class)).thenReturn(hotelDto1);

        HotelDto actualHotel = hotelService.createHotel(hotelDto1);
        assertThat(actualHotel.getHotelName()).isEqualTo(hotelDto1.getHotelName());
    }

    @Test
    void getAllHotels() {
        List<Hotel> hotelList = Arrays.asList(hotel1, hotel2);
        Page<Hotel> pageHotels = new PageImpl<>(hotelList);

        Pageable pageable = PageRequest.of(0, 2, Sort.by("hotelName"));

        when(hotelRepo.findAll(pageable)).thenReturn(pageHotels);
        when(modelMapper.map(hotel1, HotelDto.class)).thenReturn(hotelDto1);
        when(modelMapper.map(hotel2, HotelDto.class)).thenReturn(hotelDto2);

        List<HotelDto> allHotels = hotelService.getAllHotels(0, 2);

        assertThat(allHotels.get(0).getHotelName()).isEqualTo(hotel1.getHotelName());

    }

    @Test
    void getHotelById() {
        when(hotelRepo.findById("id1")).thenReturn(Optional.ofNullable(hotel1));
        when(modelMapper.map(hotel1, HotelDto.class)).thenReturn(hotelDto1);

        HotelDto actualResult = hotelService.getHotelById("id1");

        assertThat(actualResult.getHotelName()).isEqualTo(hotelDto1.getHotelName());
    }

    @Test
    void updateHotelById() {
        when(hotelRepo.findById("id1")).thenReturn(Optional.of(hotel1));
        when(hotelRepo.save(any(Hotel.class))).thenReturn(hotel1);
        when(modelMapper.map(hotel1, HotelDto.class)).thenReturn(hotelDto1);

        HotelDto actualResult = hotelService.updateHotelById("id1", hotelDto1);

        assertThat(actualResult.getHotelName()).isEqualTo(hotelDto1.getHotelName());
    }

    @Test
    void deleteHotelById() {
        when(hotelRepo.findById("id1")).thenReturn(Optional.of(hotel1));
        doNothing().when(hotelRepo).delete(hotel1);

        hotelService.deleteHotelById("id1");

        verify(hotelRepo).findById("id1");
        verify(hotelRepo).delete(hotel1);

    }

    @Test
    void doesHotelExist() {
        when(hotelRepo.existsById("id2")).thenReturn(true);

        Boolean exists = hotelService.doesHotelExist("id2");

        assertThat(exists).isTrue();
    }
}
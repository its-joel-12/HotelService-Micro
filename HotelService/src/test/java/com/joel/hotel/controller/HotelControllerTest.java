package com.joel.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.joel.hotel.dto.HotelDto;
import com.joel.hotel.model.Hotel;
import com.joel.hotel.service.HotelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
class HotelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    Hotel hotel1;
    Hotel hotel2;
    HotelDto hotelDto1;
    HotelDto hotelDto2;

    @BeforeEach
    void setUp() {
        hotel1 = new Hotel("id1", "Hotel name 1", "Hotel location 1", "Hotel about 1");
        hotel2 = new Hotel("id2", "Hotel name 2", "Hotel location 2", "Hotel about 2");
        hotelDto1 = new HotelDto("id1", "Hotel name 1", "Hotel location 1", "Hotel about 1");
        hotelDto2 = new HotelDto("id2", "Hotel name 2", "Hotel location 2", "Hotel about 2");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createHotel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(hotelDto1);

        when(hotelService.createHotel(hotelDto1)).thenReturn(hotelDto1);

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void getAllHotels() throws Exception {
        List<HotelDto> hotelDtoList = Arrays.asList(hotelDto1, hotelDto2);
        when(hotelService.getAllHotels(anyInt(), anyInt())).thenReturn(hotelDtoList);

        mockMvc.perform(get("/hotels")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getHotelById() throws Exception {
        when(hotelService.getHotelById("id1")).thenReturn(hotelDto1);

        mockMvc.perform(get("/hotels/id1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateHotelById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(hotelDto1);

        when(hotelService.updateHotelById("id1", hotelDto1)).thenReturn(hotelDto1);

        mockMvc.perform(put("/hotels/id1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testDeleteHotelById() throws Exception {
        mockMvc.perform(delete("/hotels/id1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void doesRatingExist() throws Exception {
        String hotelId = "id1";

        // Mocking the service method
        when(hotelService.doesHotelExist(hotelId)).thenReturn(true);

        mockMvc.perform(get("/hotels/exists/id1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));


        // Mocking the service method for a non-existent hotel
        when(hotelService.doesHotelExist("nonexistentId")).thenReturn(false);

        mockMvc.perform(get("/hotels/exists/id3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
    }

}
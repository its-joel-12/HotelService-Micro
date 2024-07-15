package com.joel.hotel.service.impl;

import com.joel.hotel.dto.HotelDto;
import com.joel.hotel.exception.ResourceNotFoundException;
import com.joel.hotel.model.Hotel;
import com.joel.hotel.repository.HotelRepository;
import com.joel.hotel.service.HotelService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private ModelMapper modelMapper;

    public HotelServiceImpl(HotelRepository hotelRepo, ModelMapper modelMapper) {
        this.hotelRepo = hotelRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        String randomHotelId = UUID.randomUUID().toString();
        hotelDto.setHotelId(randomHotelId);
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        Hotel savedHotel = hotelRepo.save(hotel);
        return modelMapper.map(savedHotel, HotelDto.class);
    }

    @Override
    public List<HotelDto> getAllHotels(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("hotelName"));
        Page<Hotel> allPageHotels = hotelRepo.findAll(pageable);
        List<Hotel> allHotels = allPageHotels.getContent();

        List<HotelDto> allHotelsDto = allHotels.stream()
                .map(h -> modelMapper.map(h, HotelDto.class)).collect(Collectors.toList());
        return allHotelsDto;
    }

    @Override
    public HotelDto getHotelById(String hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with the given id: " + hotelId));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Transactional
    @Override
    public HotelDto updateHotelById(String hotelId, HotelDto hotelDto) {
        Hotel hotel1 = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with the given id: " + hotelId));
        hotel1.setHotelId(hotelId);
        hotel1.setHotelName(hotelDto.getHotelName());
        hotel1.setHotelLocation(hotelDto.getHotelLocation());
        hotel1.setHotelAbout(hotelDto.getHotelAbout());

        Hotel updatedHotel = hotelRepo.save(hotel1);
        return modelMapper.map(updatedHotel, HotelDto.class);
    }

    @Transactional
    @Override
    public void deleteHotelById(String hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with the given id: " + hotelId));
        hotelRepo.delete(hotel);
    }

    @Override
    public Boolean doesHotelExist(String hotelId) {
        return hotelRepo.existsById(hotelId);
    }
}

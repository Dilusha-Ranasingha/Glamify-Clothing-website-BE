package com.example.demo.service;

import com.example.demo.dto.WomenCardDto;
import com.example.demo.model.WomenCardModel;
import com.example.demo.repo.WomenCardRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class WomenCardService {

    @Autowired
    private WomenCardRepo womenCardRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<WomenCardDto> getAllWomenCards() {
        List<WomenCardModel> cards = womenCardRepo.findAll();
        return cards.stream().map(card -> modelMapper.map(card, WomenCardDto.class)).toList();
    }

    public WomenCardDto addWomenCard(WomenCardDto womenCardDto, MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        // Convert DTO to Model
        WomenCardModel womenCardModel = modelMapper.map(womenCardDto, WomenCardModel.class);

        // Save the image as a byte array (BLOB)
        womenCardModel.setImage(image.getBytes());

        // Save card to the database
        womenCardModel = womenCardRepo.save(womenCardModel);

        // Convert Model back to DTO
        return modelMapper.map(womenCardModel, WomenCardDto.class);
    }

    public WomenCardDto updateWomenCard(int id, WomenCardDto womenCardDto, MultipartFile image) throws IOException {
        Optional<WomenCardModel> optionalCard = womenCardRepo.findById(id);
        if (optionalCard.isEmpty()) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        WomenCardModel existingCard = optionalCard.get();

        // Update card details
        existingCard.setName(womenCardDto.getName());
        existingCard.setPrice(womenCardDto.getPrice());
        existingCard.setSizes(womenCardDto.getSizes());
        existingCard.setColors(womenCardDto.getColors());

        // Update the image if provided
        if (image != null && !image.isEmpty()) {
            existingCard.setImage(image.getBytes());
        }

        // Save updated card to the database
        existingCard = womenCardRepo.save(existingCard);

        // Convert Model back to DTO
        return modelMapper.map(existingCard, WomenCardDto.class);
    }

    public void deleteWomenCard(int id) {
        if (!womenCardRepo.existsById(id)) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        womenCardRepo.deleteById(id);
    }
}

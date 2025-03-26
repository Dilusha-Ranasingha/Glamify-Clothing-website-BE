package com.example.demo.service;

import com.example.demo.dto.FootwearCardDto;
import com.example.demo.model.FootwearCardModel;
import com.example.demo.repo.FootwearCardRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FootwearCardService {

    @Autowired
    private FootwearCardRepo footwearCardRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<FootwearCardDto> getAllFootwearCards() {
        List<FootwearCardModel> cards = footwearCardRepo.findAll();
        return cards.stream().map(card -> modelMapper.map(card, FootwearCardDto.class)).toList();
    }

    public FootwearCardDto addFootwearCard(FootwearCardDto footwearCardDto, MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        // Convert DTO to Model
        FootwearCardModel footwearCardModel = modelMapper.map(footwearCardDto, FootwearCardModel.class);

        // Save the image as a byte array (BLOB)
        footwearCardModel.setImage(image.getBytes());

        // Save card to the database
        footwearCardModel = footwearCardRepo.save(footwearCardModel);

        // Convert Model back to DTO
        return modelMapper.map(footwearCardModel, FootwearCardDto.class);
    }

    public FootwearCardDto updateFootwearCard(int id, FootwearCardDto footwearCardDto, MultipartFile image) throws IOException {
        Optional<FootwearCardModel> optionalCard = footwearCardRepo.findById(id);
        if (optionalCard.isEmpty()) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        FootwearCardModel existingCard = optionalCard.get();

        // Update card details
        existingCard.setName(footwearCardDto.getName());
        existingCard.setPrice(footwearCardDto.getPrice());
        existingCard.setSizes(footwearCardDto.getSizes());
        existingCard.setColors(footwearCardDto.getColors());

        // Update the image if provided
        if (image != null && !image.isEmpty()) {
            existingCard.setImage(image.getBytes());
        }

        // Save updated card to the database
        existingCard = footwearCardRepo.save(existingCard);

        // Convert Model back to DTO
        return modelMapper.map(existingCard, FootwearCardDto.class);
    }

    public void deleteFootwearCard(int id) {
        if (!footwearCardRepo.existsById(id)) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        footwearCardRepo.deleteById(id);
    }
}

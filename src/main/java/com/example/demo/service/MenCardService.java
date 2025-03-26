package com.example.demo.service;

import com.example.demo.dto.MenCardDto;
import com.example.demo.model.MenCardModel;
import com.example.demo.repo.MenCardRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MenCardService {

    @Autowired
    private MenCardRepo menCardRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<MenCardDto> getAllMenCards() {
        List<MenCardModel> cards = menCardRepo.findAll();
        return cards.stream().map(card -> modelMapper.map(card, MenCardDto.class)).toList();
    }

    public MenCardDto addMenCard(MenCardDto menCardDto, MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        // Convert DTO to Model
        MenCardModel menCardModel = modelMapper.map(menCardDto, MenCardModel.class);

        // Save the image as a byte array (BLOB)
        menCardModel.setImage(image.getBytes());

        // Save card to the database
        menCardModel = menCardRepo.save(menCardModel);

        // Convert Model back to DTO
        return modelMapper.map(menCardModel, MenCardDto.class);
    }

    public MenCardDto updateMenCard(int id, MenCardDto menCardDto, MultipartFile image) throws IOException {
        Optional<MenCardModel> optionalCard = menCardRepo.findById(id);
        if (optionalCard.isEmpty()) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        MenCardModel existingCard = optionalCard.get();

        // Update card details
        existingCard.setName(menCardDto.getName());
        existingCard.setPrice(menCardDto.getPrice());
        existingCard.setSizes(menCardDto.getSizes());
        existingCard.setColors(menCardDto.getColors());

        // Update the image if provided
        if (image != null && !image.isEmpty()) {
            existingCard.setImage(image.getBytes());
        }

        // Save updated card to the database
        existingCard = menCardRepo.save(existingCard);

        // Convert Model back to DTO
        return modelMapper.map(existingCard, MenCardDto.class);
    }

    public void deleteMenCard(int id) {
        if (!menCardRepo.existsById(id)) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        menCardRepo.deleteById(id);
    }
}

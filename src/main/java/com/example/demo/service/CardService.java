package com.example.demo.service;

import com.example.demo.dto.CardDto;
import com.example.demo.model.CardModel;
import com.example.demo.repo.CardRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<CardDto> getAllCards() {
        List<CardModel> cards = cardRepo.findAll();
        return cards.stream().map(card -> modelMapper.map(card, CardDto.class)).toList();
    }

    public CardDto addCard(CardDto cardDto, MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        // Convert DTO to Model
        CardModel cardModel = modelMapper.map(cardDto, CardModel.class);

        // Save the image as a byte array (BLOB)
        cardModel.setImage(image.getBytes());

        // Save card to the database
        cardModel = cardRepo.save(cardModel);

        // Convert Model back to DTO
        return modelMapper.map(cardModel, CardDto.class);
    }

    public CardDto updateCard(int id, CardDto cardDto, MultipartFile image) throws IOException {
        Optional<CardModel> optionalCard = cardRepo.findById(id);
        if (optionalCard.isEmpty()) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        CardModel existingCard = optionalCard.get();

        // Update card details
        existingCard.setName(cardDto.getName());
        existingCard.setPrice(cardDto.getPrice());
        existingCard.setSizes(cardDto.getSizes());
        existingCard.setColors(cardDto.getColors());

        // Update the image if provided
        if (image != null && !image.isEmpty()) {
            existingCard.setImage(image.getBytes());
        }

        // Save updated card to the database
        existingCard = cardRepo.save(existingCard);

        // Convert Model back to DTO
        return modelMapper.map(existingCard, CardDto.class);
    }

    public void deleteCard(int id) {
        if (!cardRepo.existsById(id)) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        cardRepo.deleteById(id);
    }
}

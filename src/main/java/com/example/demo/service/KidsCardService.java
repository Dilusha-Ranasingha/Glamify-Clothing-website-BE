package com.example.demo.service;

import com.example.demo.dto.KidsCardDto;
import com.example.demo.model.KidsCardModel;
import com.example.demo.repo.KidsCardRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class KidsCardService {

    @Autowired
    private KidsCardRepo kidsCardRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<KidsCardDto> getAllKidsCards() {
        List<KidsCardModel> cards = kidsCardRepo.findAll();
        return cards.stream().map(card -> modelMapper.map(card, KidsCardDto.class)).toList();
    }

    public KidsCardDto addKidsCard(KidsCardDto kidsCardDto, MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        // Convert DTO to Model
        KidsCardModel kidsCardModel = modelMapper.map(kidsCardDto, KidsCardModel.class);

        // Save the image as a byte array (BLOB)
        kidsCardModel.setImage(image.getBytes());

        // Save card to the database
        kidsCardModel = kidsCardRepo.save(kidsCardModel);

        // Convert Model back to DTO
        return modelMapper.map(kidsCardModel, KidsCardDto.class);
    }

    public KidsCardDto updateKidsCard(int id, KidsCardDto kidsCardDto, MultipartFile image) throws IOException {
        Optional<KidsCardModel> optionalCard = kidsCardRepo.findById(id);
        if (optionalCard.isEmpty()) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        KidsCardModel existingCard = optionalCard.get();

        // Update card details
        existingCard.setName(kidsCardDto.getName());
        existingCard.setPrice(kidsCardDto.getPrice());
        existingCard.setSizes(kidsCardDto.getSizes());
        existingCard.setColors(kidsCardDto.getColors());

        // Update the image if provided
        if (image != null && !image.isEmpty()) {
            existingCard.setImage(image.getBytes());
        }

        // Save updated card to the database
        existingCard = kidsCardRepo.save(existingCard);

        // Convert Model back to DTO
        return modelMapper.map(existingCard, KidsCardDto.class);
    }

    public void deleteKidsCard(int id) {
        if (!kidsCardRepo.existsById(id)) {
            throw new IllegalArgumentException("Card not found with ID: " + id);
        }

        kidsCardRepo.deleteById(id);
    }
}

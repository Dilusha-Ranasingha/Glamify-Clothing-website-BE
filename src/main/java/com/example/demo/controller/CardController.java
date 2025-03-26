package com.example.demo.controller;

import com.example.demo.dto.CardDto;
import com.example.demo.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/getall")
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    @PostMapping("/add")
    public CardDto addCard(@RequestPart("card") String cardDtoJson,
                           @RequestPart("image") MultipartFile image) throws IOException {
        // Parse cardDtoJson into a CardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        CardDto cardDto = objectMapper.readValue(cardDtoJson, CardDto.class);

        return cardService.addCard(cardDto, image);
    }

    @PutMapping("/update/{id}")
    public CardDto updateCard(@PathVariable int id,
                              @RequestPart("card") String cardDtoJson,
                              @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Parse cardDtoJson into a CardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        CardDto cardDto = objectMapper.readValue(cardDtoJson, CardDto.class);

        return cardService.updateCard(id, cardDto, image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCard(@PathVariable int id) {
        cardService.deleteCard(id);
    }
}

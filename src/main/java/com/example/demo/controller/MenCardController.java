package com.example.demo.controller;

import com.example.demo.dto.MenCardDto;
import com.example.demo.service.MenCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/mencards")
public class MenCardController {
    @Autowired
    private MenCardService menCardService;

    @GetMapping("/getall")
    public List<MenCardDto> getAllMenCards() {
        return menCardService.getAllMenCards();
    }

    @PostMapping("/add")
    public MenCardDto addMenCard(@RequestPart("card") String menCardDtoJson,
                                 @RequestPart("image") MultipartFile image) throws IOException {
        // Parse menCardDtoJson into a MenCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        MenCardDto menCardDto = objectMapper.readValue(menCardDtoJson, MenCardDto.class);

        return menCardService.addMenCard(menCardDto, image);
    }

    @PutMapping("/update/{id}")
    public MenCardDto updateMenCard(@PathVariable int id,
                                    @RequestPart("card") String menCardDtoJson,
                                    @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Parse menCardDtoJson into a MenCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        MenCardDto menCardDto = objectMapper.readValue(menCardDtoJson, MenCardDto.class);

        return menCardService.updateMenCard(id, menCardDto, image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMenCard(@PathVariable int id) {
        menCardService.deleteMenCard(id);
    }
}

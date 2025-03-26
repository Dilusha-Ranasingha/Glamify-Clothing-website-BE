package com.example.demo.controller;

import com.example.demo.dto.KidsCardDto;
import com.example.demo.service.KidsCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/kidscards")
public class KidsCardController {
    @Autowired
    private KidsCardService kidsCardService;

    @GetMapping("/getall")
    public List<KidsCardDto> getAllKidsCards() {
        return kidsCardService.getAllKidsCards();
    }

    @PostMapping("/add")
    public KidsCardDto addKidsCard(@RequestPart("card") String kidsCardDtoJson,
                                   @RequestPart("image") MultipartFile image) throws IOException {
        // Parse kidsCardDtoJson into a KidsCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        KidsCardDto kidsCardDto = objectMapper.readValue(kidsCardDtoJson, KidsCardDto.class);

        return kidsCardService.addKidsCard(kidsCardDto, image);
    }

    @PutMapping("/update/{id}")
    public KidsCardDto updateKidsCard(@PathVariable int id,
                                      @RequestPart("card") String kidsCardDtoJson,
                                      @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Parse kidsCardDtoJson into a KidsCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        KidsCardDto kidsCardDto = objectMapper.readValue(kidsCardDtoJson, KidsCardDto.class);

        return kidsCardService.updateKidsCard(id, kidsCardDto, image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteKidsCard(@PathVariable int id) {
        kidsCardService.deleteKidsCard(id);
    }
}

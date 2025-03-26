package com.example.demo.controller;

import com.example.demo.dto.WomenCardDto;
import com.example.demo.service.WomenCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/womencards")
public class WomenCardController {
    @Autowired
    private WomenCardService womenCardService;

    @GetMapping("/getall")
    public List<WomenCardDto> getAllWomenCards() {
        return womenCardService.getAllWomenCards();
    }

    @PostMapping("/add")
    public WomenCardDto addWomenCard(@RequestPart("card") String womenCardDtoJson,
                                     @RequestPart("image") MultipartFile image) throws IOException {
        // Parse womenCardDtoJson into a WomenCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        WomenCardDto womenCardDto = objectMapper.readValue(womenCardDtoJson, WomenCardDto.class);

        return womenCardService.addWomenCard(womenCardDto, image);
    }

    @PutMapping("/update/{id}")
    public WomenCardDto updateWomenCard(@PathVariable int id,
                                        @RequestPart("card") String womenCardDtoJson,
                                        @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Parse womenCardDtoJson into a WomenCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        WomenCardDto womenCardDto = objectMapper.readValue(womenCardDtoJson, WomenCardDto.class);

        return womenCardService.updateWomenCard(id, womenCardDto, image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWomenCard(@PathVariable int id) {
        womenCardService.deleteWomenCard(id);
    }
}

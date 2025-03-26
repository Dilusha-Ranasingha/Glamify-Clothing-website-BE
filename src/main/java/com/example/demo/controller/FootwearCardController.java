package com.example.demo.controller;

import com.example.demo.dto.FootwearCardDto;
import com.example.demo.service.FootwearCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/footwearcards")
public class FootwearCardController {
    @Autowired
    private FootwearCardService footwearCardService;

    @GetMapping("/getall")
    public List<FootwearCardDto> getAllFootwearCards() {
        return footwearCardService.getAllFootwearCards();
    }

    @PostMapping("/add")
    public FootwearCardDto addFootwearCard(@RequestPart("card") String footwearCardDtoJson,
                                           @RequestPart("image") MultipartFile image) throws IOException {
        // Parse footwearCardDtoJson into a FootwearCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        FootwearCardDto footwearCardDto = objectMapper.readValue(footwearCardDtoJson, FootwearCardDto.class);

        return footwearCardService.addFootwearCard(footwearCardDto, image);
    }

    @PutMapping("/update/{id}")
    public FootwearCardDto updateFootwearCard(@PathVariable int id,
                                              @RequestPart("card") String footwearCardDtoJson,
                                              @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Parse footwearCardDtoJson into a FootwearCardDto object
        ObjectMapper objectMapper = new ObjectMapper();
        FootwearCardDto footwearCardDto = objectMapper.readValue(footwearCardDtoJson, FootwearCardDto.class);

        return footwearCardService.updateFootwearCard(id, footwearCardDto, image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFootwearCard(@PathVariable int id) {
        footwearCardService.deleteFootwearCard(id);
    }
}

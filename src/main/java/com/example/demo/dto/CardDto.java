package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private int id;
    private String name;
    private String price;
    private List<String> sizes;
    private List<String> colors;
    private byte[] image; // Store image as a byte array (for frontend display)
}

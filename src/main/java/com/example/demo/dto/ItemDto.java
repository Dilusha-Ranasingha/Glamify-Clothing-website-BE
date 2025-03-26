package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private int id;
    private String name;
    private String color;
    private String size;
    private int price;
    private int quantity;
    private byte[] image; // Change image to byte[] to store binary data
    private String userEmail;
}

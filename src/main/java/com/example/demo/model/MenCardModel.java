package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenCardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String price;

    @ElementCollection
    private List<String> sizes;

    @ElementCollection
    private List<String> colors;

    @Lob // Use @Lob to store large binary data
    private byte[] image; // Image stored as BLOB
}

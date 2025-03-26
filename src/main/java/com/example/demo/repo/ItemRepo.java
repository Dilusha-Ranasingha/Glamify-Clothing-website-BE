package com.example.demo.repo;

import com.example.demo.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<ItemModel, Integer> {
    // Custom query to fetch items based on the user's email
    List<ItemModel> findByUserEmail(String userEmail);
}
package com.example.demo.repo;

import com.example.demo.model.FootwearCardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootwearCardRepo extends JpaRepository<FootwearCardModel, Integer> {
}

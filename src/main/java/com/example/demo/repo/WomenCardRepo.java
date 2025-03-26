package com.example.demo.repo;

import com.example.demo.model.WomenCardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WomenCardRepo extends JpaRepository<WomenCardModel, Integer> {
}

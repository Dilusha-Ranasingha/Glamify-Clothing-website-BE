package com.example.demo.repo;

import com.example.demo.model.MenCardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenCardRepo extends JpaRepository<MenCardModel, Integer> {
}

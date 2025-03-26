package com.example.demo.repo;

import com.example.demo.model.KidsCardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidsCardRepo extends JpaRepository<KidsCardModel, Integer> {
}

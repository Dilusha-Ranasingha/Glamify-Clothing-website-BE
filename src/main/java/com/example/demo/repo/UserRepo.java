package com.example.demo.repo;

import com.example.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {
    // Custom query for login
    @Query("SELECT u FROM UserModel u WHERE u.email = :email AND u.password = :password")
    UserModel findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}

package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.UserModel;
import com.example.demo.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDto> getAllUsers() {
        List<UserModel> UserList = userRepo.findAll();
        return modelMapper.map(UserList , new TypeToken<List<UserDto>>(){}.getType());
    }

    public UserDto getUserById(int id) {
        UserModel user = userRepo.findById(id).orElse(null); // Handle null case safely
        return modelMapper.map(user, new TypeToken<UserDto>(){}.getType());
    }

    public UserDto addUser(UserDto userDto) {
        userRepo.save(modelMapper.map(userDto, UserModel.class));
        return userDto;
    }

    public UserDto updateUser(UserDto userDto) {
        userRepo.save(modelMapper.map(userDto, UserModel.class));
        return userDto;
    }

    public String deleteUser(UserDto userDto) {
        userRepo.delete(modelMapper.map(userDto, UserModel.class));
        return "User deleted";
    }

    public UserDto loginUser(UserDto userDto) {
        UserModel userModel = userRepo.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        if (userModel != null) {
            return modelMapper.map(userModel, UserDto.class);
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }





}

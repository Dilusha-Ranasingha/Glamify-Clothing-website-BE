package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import com.example.demo.model.ItemModel;
import com.example.demo.repo.ItemRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ItemDto> getAllItems() {
        List<ItemModel> itemList = itemRepo.findAll();
        List<ItemDto> itemDtos = modelMapper.map(itemList, new TypeToken<List<ItemDto>>() {}.getType());

        // Convert images to Base64 for frontend display
        for (ItemDto itemDto : itemDtos) {
            if (itemDto.getImage() != null) {
                itemDto.setImage(Base64.getEncoder().encode(itemDto.getImage()));
            }
        }
        return itemDtos;
    }

    public List<ItemDto> getItemsByUserEmail(String email) {
        List<ItemModel> items = itemRepo.findByUserEmail(email);
        List<ItemDto> itemDtos = modelMapper.map(items, new TypeToken<List<ItemDto>>() {}.getType());

        // Convert images to Base64 for frontend display
        for (ItemDto itemDto : itemDtos) {
            if (itemDto.getImage() != null) {
                itemDto.setImage(Base64.getEncoder().encode(itemDto.getImage()));
            }
        }
        return itemDtos;
    }

    public ItemDto getItemById(int id) {
        ItemModel itemModel = itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        ItemDto itemDto = modelMapper.map(itemModel, ItemDto.class);

        // Convert image to Base64 for frontend display
        if (itemDto.getImage() != null) {
            itemDto.setImage(Base64.getEncoder().encode(itemDto.getImage()));
        }
        return itemDto;
    }

    public ItemDto addItem(ItemDto itemDto) {
        // Convert DTO to Model
        ItemModel itemModel = modelMapper.map(itemDto, ItemModel.class);

        // Save item to the database
        itemRepo.save(itemModel);

        // Convert Model back to DTO
        return itemDto;
    }

    public String deleteItemById(int id) {
        Optional<ItemModel> item = itemRepo.findById(id);
        if (item.isPresent()) {
            itemRepo.deleteById(id);
            return "Item deleted successfully";
        } else {
            throw new RuntimeException("Item not found");
        }
    }

    public String updateQuantity(int id, int quantity) {
        Optional<ItemModel> itemOptional = itemRepo.findById(id);
        if (itemOptional.isPresent()) {
            ItemModel item = itemOptional.get();
            item.setQuantity(quantity);
            itemRepo.save(item);
            return "Quantity updated";
        }
        throw new RuntimeException("Item not found");
    }
}

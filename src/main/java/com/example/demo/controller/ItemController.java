package com.example.demo.controller;

import com.example.demo.dto.ItemDto;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/getitems")
    public List<ItemDto> getItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/getitemsbyuser")
    public List<ItemDto> getItemsByUserEmail(@RequestParam String email) {
        return itemService.getItemsByUserEmail(email);
    }

    @GetMapping("/getitembyid")
    public ItemDto getItemById(@RequestBody ItemDto itemDto) {
        return itemService.getItemById(itemDto.getId());
    }

    @PutMapping("/updatequantity")
    public String updateQuantity(@RequestParam int id, @RequestParam int quantity) {
        return itemService.updateQuantity(id, quantity);
    }

    @PostMapping("/additem")
    public ItemDto addItem(@RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto);
    }

    @DeleteMapping("/deleteitem")
    public String deleteItem(@RequestParam int id) {
        return itemService.deleteItemById(id);
    }

}
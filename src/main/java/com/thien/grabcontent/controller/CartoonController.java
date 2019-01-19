package com.thien.grabcontent.controller;

import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.service.CartoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/cartoon")
public class CartoonController {

    @Autowired
    CartoonService cartoonService;

    @PostMapping
    public void createCartoon(@RequestBody CartoonInfoDTO cartoonInfo){
        cartoonService.createCartoon(cartoonInfo);
    }

    @GetMapping("/{id}")
    public CartoonInfo getCartoonById(@PathVariable("id") Long id){
        return cartoonService.getCartoonById(id);
    }

    @GetMapping
    public List<CartoonInfo> getAllCartoons(){
        return cartoonService.getAllCartoons();
    }

    @PutMapping
    public void updateCartoon(@RequestBody CartoonInfoDTO cartoonInfo){
        cartoonService.updateCartoon(cartoonInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteCartoon(@PathVariable("id") Long id){
        cartoonService.deleteCartoon(id);
    }

}

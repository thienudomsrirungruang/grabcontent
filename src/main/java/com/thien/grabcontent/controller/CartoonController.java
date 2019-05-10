package com.thien.grabcontent.controller;

import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.service.CartoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartoon")
public class CartoonController {

    @Autowired
    CartoonService cartoonService;

    @PostMapping
    public void createCartoon(@RequestBody CartoonInfoDTO cartoonInfo){
        cartoonService.createCartoon(cartoonInfo);
    }

    @GetMapping("/{id}")
    public CartoonInfoDTO getCartoonById(@PathVariable("id") Long id){
        return cartoonService.getCartoonById(id);
    }

    @GetMapping("/page/{page}")
    public List<CartoonInfoDTO> getAllCartoons(@PathVariable("page") int page){
        return cartoonService.getAllCartoons(page);
    }

    @PutMapping
    public void updateCartoon(@RequestBody CartoonInfoDTO cartoonInfo){
        cartoonService.updateCartoon(cartoonInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteCartoon(@PathVariable("id") Long id){
        cartoonService.deleteCartoon(id);
    }

    @GetMapping("/cartoonNames")
    public List<String> getCartoonNames(){
        return cartoonService.getCartoonNames();
    }

    @GetMapping("/byName/{cartoon}")
    public List<CartoonInfoDTO> getCartoonsByName(@PathVariable("cartoon") String cartoonName){
        return cartoonService.getCartoonsByName(cartoonName);
    }
}

package com.thien.grabcontent.controller;

import com.thien.grabcontent.dto.CartoonInfoDTO;
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
    public void createCartoon(@RequestBody CartoonInfoDTO cartoonInfo) {
        cartoonService.createCartoon(cartoonInfo);
    }

    @GetMapping("/{id}")
    public CartoonInfoDTO getCartoonById(@PathVariable("id") Long id) {
        return cartoonService.getCartoonById(id);
    }

    @GetMapping
    public List<CartoonInfoDTO> getAllCartoons(@RequestParam String page) {
        int pageInt = Integer.parseInt(page);
        return cartoonService.getAllCartoons(pageInt);
    }

    @PutMapping
    public void updateCartoon(@RequestBody CartoonInfoDTO cartoonInfo) {
        cartoonService.updateCartoon(cartoonInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteCartoon(@PathVariable("id") Long id) {
        cartoonService.deleteCartoon(id);
    }

    @GetMapping("/cartoonNames")
    public List<String> getCartoonNames() {
        return cartoonService.getCartoonNames();
    }

    @GetMapping("/byName/{cartoon}")
    public List<CartoonInfoDTO> getCartoonsByName(@PathVariable("cartoon") String cartoonName) {
        return cartoonService.getCartoonsByName(cartoonName);
    }

    @GetMapping("/byViews")
    @ResponseBody
    public List<CartoonInfoDTO> getCartoonsByViews(@RequestParam String page) {
        int pageInt = Integer.parseInt(page);
        return cartoonService.getCartoonsByViews(pageInt);
    }
}

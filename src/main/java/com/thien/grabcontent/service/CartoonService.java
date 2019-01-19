package com.thien.grabcontent.service;

import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.repository.CartoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartoonService {

    @Autowired
    CartoonRepository cartoonRepository;

    @Transactional
    public void createCartoon(CartoonInfoDTO cartoonInfoDTO){
        cartoonRepository.save(toEntity(cartoonInfoDTO));
    }

    public CartoonInfo getCartoonById(Long id){
        Optional<CartoonInfo> result = cartoonRepository.findById(id);
        return result.orElse(null);
    }

    public List<CartoonInfo> getAllCartoons(){
        ArrayList<CartoonInfo> result = new ArrayList<>();
        cartoonRepository.findAll().forEach(result::add); //converts Iterator to ArrayList
        return result;
    }

    @Transactional
    public void updateCartoon(CartoonInfoDTO cartoonInfoDTO){
        Optional<CartoonInfo> optionalCartoon = cartoonRepository.findById(cartoonInfoDTO.getId());
        if(optionalCartoon.isPresent()) {
            CartoonInfo existingCartoon = optionalCartoon.get();
            existingCartoon.setCartoonName(cartoonInfoDTO.getCartoonName());
            existingCartoon.setChapter(cartoonInfoDTO.getChapter());
            existingCartoon.setEndpoint(cartoonInfoDTO.getEndpoint());
        }
    }

    @Transactional
    public void deleteCartoon(Long id){
        cartoonRepository.deleteById(id);
    }

    private CartoonInfo toEntity(CartoonInfoDTO cartoonInfoDTO) {
        CartoonInfo cartoonInfo = new CartoonInfo();
        cartoonInfo.setId(cartoonInfoDTO.getId());
        cartoonInfo.setCartoonName(cartoonInfoDTO.getCartoonName());
        cartoonInfo.setChapter(cartoonInfoDTO.getChapter());
        cartoonInfo.setEndpoint(cartoonInfoDTO.getEndpoint());
        return  cartoonInfo;
    }
}

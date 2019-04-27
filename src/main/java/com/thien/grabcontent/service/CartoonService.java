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

    @Transactional
    public CartoonInfoDTO getCartoonById(Long id){
        Optional<CartoonInfo> result = cartoonRepository.findById(id);
        if(result.isPresent()){
            CartoonInfo resultCartoonInfo = result.get();
            resultCartoonInfo.setViews(resultCartoonInfo.getViews() + 1);
            return toDTO(resultCartoonInfo);
        }else{
            return null;
        }
    }

    public List<CartoonInfoDTO> getAllCartoons(){
        ArrayList<CartoonInfo> result = new ArrayList<>();
        cartoonRepository.findAll().forEach(result::add); //converts Iterator to ArrayList
        ArrayList<CartoonInfoDTO> output = new ArrayList<>();
        for (CartoonInfo cartoonInfo : result) {
            output.add(toDTO(cartoonInfo));
        }
        return output;
    }

    @Transactional
    public void updateCartoon(CartoonInfoDTO cartoonInfoDTO){
        Optional<CartoonInfo> optionalCartoon = cartoonRepository.findById(cartoonInfoDTO.getId());
        if(optionalCartoon.isPresent()) {
            CartoonInfo existingCartoon = optionalCartoon.get();
            existingCartoon.setCartoonName(cartoonInfoDTO.getCartoonName());
            existingCartoon.setChapter(cartoonInfoDTO.getChapter());
        }
    }

    @Transactional
    public void deleteCartoon(Long id){
        cartoonRepository.deleteById(id);
    }

    CartoonInfo toEntity(CartoonInfoDTO cartoonInfoDTO) {
        CartoonInfo cartoonInfo = new CartoonInfo();
        cartoonInfo.setId(cartoonInfoDTO.getId());
        cartoonInfo.setCartoonName(cartoonInfoDTO.getCartoonName());
        cartoonInfo.setChapter(cartoonInfoDTO.getChapter());
        cartoonInfo.setViews(cartoonInfoDTO.getViews());
        return cartoonInfo;
    }

    CartoonInfoDTO toDTO (CartoonInfo cartoonInfo){
        CartoonInfoDTO cartoonInfoDTO = new CartoonInfoDTO();
        cartoonInfoDTO.setId(cartoonInfo.getId());
        cartoonInfoDTO.setCartoonName(cartoonInfo.getCartoonName());
        cartoonInfoDTO.setChapter(cartoonInfo.getChapter());
        cartoonInfoDTO.setViews(cartoonInfo.getViews());
        return cartoonInfoDTO;
    }

    public List<String> getCartoonNames() {
        return cartoonRepository.findDistinctCartoonName();
    }

    public List<CartoonInfoDTO> getCartoonsByName(String cartoonName) {
        ArrayList<CartoonInfo> result = new ArrayList<>();
        result.addAll(cartoonRepository.findByCartoonNameOrderByChapterDesc(cartoonName));
        ArrayList<CartoonInfoDTO> output = new ArrayList<>();
        for (CartoonInfo cartoonInfo : result){
            output.add(toDTO(cartoonInfo));
        }
        return output;
    }
}

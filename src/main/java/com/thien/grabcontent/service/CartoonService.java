package com.thien.grabcontent.service;

import com.thien.grabcontent.config.CartoonProperties;
import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.entity.PageInfo;
import com.thien.grabcontent.repository.CartoonRepository;
import com.thien.grabcontent.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartoonService {

    @Autowired
    private CartoonRepository cartoonRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private CartoonProperties cartoonProperties;

    @Transactional
    public void createCartoon(CartoonInfoDTO cartoonInfoDTO) {
        cartoonRepository.save(toEntity(cartoonInfoDTO));
    }

    @Transactional
    public CartoonInfoDTO getCartoonById(Long id) {
        Optional<CartoonInfo> result = cartoonRepository.findById(id);
        if (result.isPresent()) {
            CartoonInfo resultCartoonInfo = result.get();
            resultCartoonInfo.setViews(resultCartoonInfo.getViews() + 1);
            return toDTO(resultCartoonInfo);
        } else {
            return null;
        }
    }

    public List<CartoonInfoDTO> getAllCartoons(int page) {
        List<CartoonInfo> result = cartoonRepository.findAllByOrderByCreateDateDesc(PageRequest.of(page, cartoonProperties.getPageSize()));
        ArrayList<CartoonInfoDTO> output = new ArrayList<>();
        for (CartoonInfo cartoonInfo : result) {
            output.add(toDTO(cartoonInfo));
        }
        return output;
    }

    public List<CartoonInfoDTO> getCartoonsByViews(int page) {
        //converts Iterator to ArrayList
        ArrayList<CartoonInfo> result = new ArrayList<>(cartoonRepository.findAllByOrderByViewsDescIdDesc(PageRequest.of(page, cartoonProperties.getPageSize())));
        ArrayList<CartoonInfoDTO> output = new ArrayList<>();
        for (CartoonInfo cartoonInfo : result) {
            output.add(toDTO(cartoonInfo));
        }
        return output;
    }

    @Transactional
    public void updateCartoon(CartoonInfoDTO cartoonInfoDTO) {
        Optional<CartoonInfo> optionalCartoon = cartoonRepository.findById(cartoonInfoDTO.getId());
        if (optionalCartoon.isPresent()) {
            CartoonInfo existingCartoon = optionalCartoon.get();
            existingCartoon.setCartoonName(cartoonInfoDTO.getCartoonName());
            existingCartoon.setChapter(cartoonInfoDTO.getChapter());
        }
    }

    @Transactional
    public void deleteCartoon(Long id) {
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

    CartoonInfoDTO toDTO(CartoonInfo cartoonInfo) {
        CartoonInfoDTO cartoonInfoDTO = new CartoonInfoDTO();
        cartoonInfoDTO.setId(cartoonInfo.getId());
        cartoonInfoDTO.setCartoonName(cartoonInfo.getCartoonName());
        cartoonInfoDTO.setChapter(cartoonInfo.getChapter());
        cartoonInfoDTO.setViews(cartoonInfo.getViews());
        cartoonInfoDTO.setFirstPageUrl(cartoonInfo.getPageInfoList().size() == 0 ? null : cartoonInfo.getPageInfoList().get(0).getPageUrl());
        return cartoonInfoDTO;
    }

    public List<String> getCartoonNames() {
        return cartoonRepository.findDistinctCartoonName();
    }

    public List<CartoonInfoDTO> getCartoonsByName(String cartoonName) {
        ArrayList<CartoonInfo> result = new ArrayList<>();
        result.addAll(cartoonRepository.findByCartoonNameOrderByChapterDesc(cartoonName));
        ArrayList<CartoonInfoDTO> output = new ArrayList<>();
        for (CartoonInfo cartoonInfo : result) {
            output.add(toDTO(cartoonInfo));
        }
        return output;
    }

    public List<String> getPageUrlByCartoonChapter(String cartoon, int chapterInt) {
        Optional<CartoonInfo> optionalCartoon = cartoonRepository.findByCartoonNameAndChapterOrderByPageNumber(cartoon, chapterInt);
        if (optionalCartoon.isPresent()) {
            CartoonInfo cartoonInfo = optionalCartoon.get();
            List<PageInfo> pageInfoList = cartoonInfo.getPageInfoList();
            ArrayList<String> urlList = new ArrayList<>();
            for (PageInfo pageInfo : pageInfoList) {
                urlList.add(pageInfo.getPageUrl());
            }
            return urlList;
        }
        return null;
    }
}

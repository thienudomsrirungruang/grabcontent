package com.thien.grabcontent.service;

import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.dto.PageInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.entity.PageInfo;
import com.thien.grabcontent.repository.CartoonRepository;
import com.thien.grabcontent.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    PageRepository pageRepository;

    @Autowired
    CartoonRepository cartoonRepository;

    @Transactional
    public void createPage(PageInfoDTO pageInfoDTO) throws Exception {
        pageRepository.save(toEntity(pageInfoDTO));
    }

    public PageInfoDTO getPageById(Long id){
        Optional<PageInfo> result = pageRepository.findById(id);
        if(result.isPresent()){
            return toDTO(result.get());
        }else{
            return null;
        }
    }

    public List<PageInfoDTO> getAllPages(){
        ArrayList<PageInfo> result = new ArrayList<>();
        pageRepository.findAll().forEach(result::add); //converts Iterator to ArrayList
        ArrayList<PageInfoDTO> output = new ArrayList<>();
        for (PageInfo pageInfo : result) {
            output.add(toDTO(pageInfo));
        }
        return output;
    }

    @Transactional
    public void updatePage(PageInfoDTO pageInfoDTO){
        Optional<PageInfo> optionalPage = pageRepository.findById(pageInfoDTO.getId());
        Optional<CartoonInfo> cartoonInfo = cartoonRepository.findById(pageInfoDTO.getCartoonId());
        if(optionalPage.isPresent() && cartoonInfo.isPresent()) {
            PageInfo existingPage = optionalPage.get();
            existingPage.setCartoonInfo(cartoonInfo.get());
            existingPage.setPageUrl(pageInfoDTO.getPageUrl());
            existingPage.setPageNumber(pageInfoDTO.getPageNumber());
        }
    }

    @Transactional
    public void deletePage(Long id){
        pageRepository.deleteById(id);
    }

    PageInfo toEntity(PageInfoDTO pageInfoDTO) throws Exception {
        Optional<CartoonInfo> cartoonInfo = cartoonRepository.findById(pageInfoDTO.getCartoonId());
        if(cartoonInfo.isPresent()) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setId(pageInfoDTO.getId());
            pageInfo.setCartoonInfo(cartoonInfo.get());
            pageInfo.setPageNumber(pageInfoDTO.getPageNumber());
            pageInfo.setPageUrl(pageInfoDTO.getPageUrl());
            return pageInfo;
        }
        throw new Exception("Cartoon with specified id not found");
    }

     PageInfoDTO toDTO(PageInfo pageInfo){
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        pageInfoDTO.setId(pageInfo.getId());
        pageInfoDTO.setCartoonId(pageInfo.getCartoonInfo().getId());
        pageInfoDTO.setPageNumber(pageInfo.getPageNumber());
        pageInfoDTO.setPageUrl(pageInfo.getPageUrl());
        return pageInfoDTO;
    }
}

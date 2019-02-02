package com.thien.grabcontent.controller;

import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.dto.PageInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/page")
public class PageController {

    @Autowired
    PageService pageService;

    @PostMapping
    public void createPage(@RequestBody PageInfoDTO pageInfo) throws Exception {
        pageService.createPage(pageInfo);
    }

    @GetMapping("/{id}")
    public PageInfoDTO getPageById(@PathVariable("id") Long id){
        return pageService.getPageById(id);
    }

    @GetMapping
    public List<PageInfoDTO> getAllPages(){
        return pageService.getAllPages();
    }

    @PutMapping
    public void updatePage(@RequestBody PageInfoDTO pageInfoDTO){
        pageService.updatePage(pageInfoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePage(@PathVariable("id") Long id){
        pageService.deletePage(id);
    }

}

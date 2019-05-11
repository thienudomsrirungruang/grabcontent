package com.thien.grabcontent.controller;


import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.service.CartoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    CartoonService cartoonService;

    @GetMapping("/")
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/api/cartoon/byViews")
    @ResponseBody
    public List<CartoonInfoDTO> getCartoonsByViews(@RequestParam String page) {
        int pageInt = Integer.parseInt(page);
        return cartoonService.getCartoonsByViews(pageInt);
    }
}

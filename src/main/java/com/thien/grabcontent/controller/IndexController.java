package com.thien.grabcontent.controller;


import com.thien.grabcontent.service.CartoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    CartoonService cartoonService;

    @GetMapping("/")
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/{cartoonName}/{chapter}")
    public String getChapterPage() {
        return "chapter";
    }
}

package com.thien.grabcontent.controller;

import com.thien.grabcontent.service.GrabService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/grab/")
public class GrabController {

    private final GrabService grabService;

    public GrabController(GrabService grabService) {
        this.grabService = grabService;
    }

    @PostMapping("/{cartoonName}")
    public void getCartoon(@PathVariable("cartoonName") String cartoonName) throws Exception {
        grabService.getLatestCartoon(cartoonName);
    }
}

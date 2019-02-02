package com.thien.grabcontent.service;

import com.thien.grabcontent.config.CartoonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrabService {

    @Autowired
    private CartoonProperties cartoonProperties;

    public void testProp() {
        System.out.println("test");
    }
}

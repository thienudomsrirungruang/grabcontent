package com.thien.grabcontent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
public class CartoonProperties {

    List<CartoonConfig> cartoonList = new ArrayList<>();

    public List<CartoonConfig> getCartoonList() {
        return cartoonList;
    }

    public void setCartoonList(List<CartoonConfig> cartoonList) {
        this.cartoonList = cartoonList;
    }
}

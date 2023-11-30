package com.dev.shop.seller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @GetMapping("/login")
    public void sellerGet(){
        
    }

    @PostMapping("/login")
    public void sellerPost() {

    }
}

package com.garud.retail.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Greetings {



    @GetMapping("/hello")
    public String getGreeting(){
        return "hello";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndPoint(){
        return "hello, user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndPoint(){
        return "hello, admin";
    }



}

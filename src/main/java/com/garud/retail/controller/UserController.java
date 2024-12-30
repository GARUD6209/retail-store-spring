package com.garud.retail.controller;

import com.garud.retail.jwt.JwtUtils;
import com.garud.retail.pojo.UserResponse;
import com.garud.retail.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getUser(HttpServletRequest request){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromHeader(request));

        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserResponse userResponse,HttpServletRequest request){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromHeader(request));
        return new ResponseEntity<>(userService.updateUser(userResponse,username), HttpStatus.OK);
    }


}

package com.garud.retail.controller;

import com.garud.retail.pojo.UserResponse;
import com.garud.retail.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,@RequestBody UserResponse userResponse){

        return new ResponseEntity<>(userService.updateUser(id,userResponse), HttpStatus.OK);
    }

    @PostMapping("/otp/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> saveOTP(@PathVariable String email) {
        boolean otpStatus = userService.saveOTP(email);

        if (otpStatus)
            return new ResponseEntity<>("Otp sent successfully.",HttpStatus.CREATED);
        return new ResponseEntity<>("Email Does not Exist.",HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/verify-otp")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        log.info("Verifying OTP for email: {}", email);
        boolean isValid = userService.verifyOTP(email, otp);
        return ResponseEntity.ok(isValid);
    }


    @PostMapping("/update-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        log.info("Updating password for email: {}", email);
        int result = userService.updatePassword(email, otp, newPassword);
        if (result == 1) {
            return ResponseEntity.ok("Password updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update password. Invalid OTP or email.");
    }
}

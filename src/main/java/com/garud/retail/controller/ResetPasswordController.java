package com.garud.retail.controller;

import com.garud.retail.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
@Slf4j
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/otp/{email}")
    public ResponseEntity<String> saveOTP(@PathVariable String email) {
        boolean otpStatus = userService.saveOTP(email);

        if (otpStatus)
            return new ResponseEntity<>("Otp sent successfully.", HttpStatus.CREATED);
        return new ResponseEntity<>("Email Does not Exist.",HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        log.info("Verifying OTP for email: {}", email);
        boolean isValid = userService.verifyOTP(email, otp);

        if(isValid)
            return new ResponseEntity("Otp is valid.",HttpStatus.OK);
        return new ResponseEntity<>("Otp is invalid.",HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        log.info("Updating password for email: {}", email);

        int result = userService.updatePassword(email, otp, passwordEncoder.encode(newPassword));
        if (result == 1) {
            return ResponseEntity.ok("Password updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update password. Invalid OTP or email.");
    }
}

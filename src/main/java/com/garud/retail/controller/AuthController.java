package com.garud.retail.controller;

import com.garud.retail.jwt.JwtUtils;
import com.garud.retail.pojo.LoginRequest;
import com.garud.retail.pojo.LoginResponse;
import com.garud.retail.pojo.SignupRequest;
import com.garud.retail.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        // Check if user already exists
        if (((JdbcUserDetailsManager) userDetailsService).userExists(signupRequest.getUsername())) {

            return ResponseEntity.badRequest().body("Username already exists");
        }

        String save = authService.save(signupRequest);
        // Create a new user


        if (((JdbcUserDetailsManager) userDetailsService).userExists(signupRequest.getUsername())) {
            UserDetails user = User.withUsername(signupRequest.getUsername())
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .roles("USER")
                    .build();
            ((JdbcUserDetailsManager) userDetailsService).updateUser(user);
            log.info("user created with username : " + signupRequest.getUsername());
        }
        return ResponseEntity.ok("User registered successfully" + "from authService : " + save);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new
                            UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
            log.info("got to login try block");
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            log.error("got to login catch block : " + exception.getMessage());
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(
                      jwtToken,
                      userDetails.getUsername(),
                      roles );

        return ResponseEntity.ok(response);
    }
}

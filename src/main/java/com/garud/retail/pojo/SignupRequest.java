package com.garud.retail.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class SignupRequest {
    private Long id;
    private String username;
    private String password;
    private Long cityId;
    private String name;
    private String mobileNumber;
    private String email;
    private String gstNumber;
    private String address;
    private String otp;
    private boolean enabled;
}

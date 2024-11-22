package com.garud.retail.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class SignupResponse {

    private Long id;
    private String username;
    private String password;
    private Long city_id;
    private String name;
    private String mobile_number;
    private String email;
    private String gst_number;
    private String address;
    private Timestamp create_datetime;
    private Timestamp update_datetime;
    private String otp;
    private Timestamp otp_creation_time;
    private boolean enabled;

}

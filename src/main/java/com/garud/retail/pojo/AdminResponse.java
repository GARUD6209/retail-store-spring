package com.garud.retail.pojo;

import lombok.Data;

@Data
public class AdminResponse {
    private String username;
    private String password;
    private Long cityId;
    private String name;
    private String mobileNumber;
    private String email;
    private String gstNumber;
    private String address;
}

package com.garud.retail.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class SignupResponse {

    private Long id;
    private String role;

}

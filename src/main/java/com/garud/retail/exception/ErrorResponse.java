package com.garud.retail.exception;

import lombok.Data;


@Data
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;


}

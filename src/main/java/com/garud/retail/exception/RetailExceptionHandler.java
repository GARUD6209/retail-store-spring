package com.garud.retail.exception;


import com.garud.retail.constant.ErrorCodeEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RetailExceptionHandler {


    @ExceptionHandler(RetailException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(RetailException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getErrorCode());
        errorResponse.setErrorMessage(ex.getErrorMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, ex.getHttpStatus());

        return responseEntity;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(AuthenticationException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("3001");
        errorResponse.setErrorMessage(ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(AccessDeniedException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("4001");
        errorResponse.setErrorMessage(ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

        return responseEntity;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(BadCredentialsException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCodeEnum.USER_NOT_FOUND.getErrorCode());
        errorResponse.setErrorMessage(ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

        return responseEntity;
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(SignatureException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCodeEnum.INVALID_JWT_TOKEN.getErrorCode());
        errorResponse.setErrorMessage(ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(ExpiredJwtException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCodeEnum.INVALID_JWT_TOKEN.getErrorCode());
        errorResponse.setErrorMessage(ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCodeEnum.GENERIC_ERROR.getErrorCode());
//        errorResponse.setErrorMessage(ErrorCodeEnum.GENERIC_ERROR.getErrorMessage());
        errorResponse.setErrorMessage(ex.getMessage());

        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        return responseEntity;
    }

}

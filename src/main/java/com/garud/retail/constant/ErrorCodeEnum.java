package com.garud.retail.constant;


import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    
	GENERIC_ERROR("2000","Unable To Process, try later"),
	USER_ALDEADY_EXIST("20001", "Invalid Request, User already exist."),
	HEADER_IS_NULL("20003", "Authorization header is missing"),
	JWT_IS_NULL("2004","jwt was null."), 
	INVALID_JWT_TOKEN("2005","Invalid jwt token"),
	JWT_TOKEN_EXPIRE("2006","jwt token expired."),
	UNSUPPORTED_JWT("2007","jwt token unsupported."),
	USER_NOT_FOUND("2008","user not found." );

	private String errorCode;
	private String errorMessage;

	ErrorCodeEnum(String errorCode, String errorMessage) {
		 this.errorCode = errorCode;
	     this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public static ErrorCodeEnum fromErrorCode(String errorCode) {
		for (ErrorCodeEnum error : ErrorCodeEnum.values()) {
			if (error.getErrorCode().equals(errorCode)) {
				return error;
			}
		}
		throw new IllegalArgumentException("No matching enum constant for errorCode: " + errorCode);
	}

	public static ErrorCodeEnum fromErrorMessage(String errorMessage) {
		for (ErrorCodeEnum error : ErrorCodeEnum.values()) {
			if (error.getErrorMessage().equalsIgnoreCase(errorMessage)) {
				return error;
			}
		}
		throw new IllegalArgumentException("No matching enum constant for errorMessage: " + errorMessage);
	}

}
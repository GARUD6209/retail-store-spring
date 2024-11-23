package com.garud.retail.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RetailException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 5460057024243093828L;

	private final String errorCode;
	private final String errorMessage;
	
	private final HttpStatus httpStatus;

}

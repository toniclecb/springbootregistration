package com.toniclecb.springbootregistration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsuportedDateFormatException extends RuntimeException {
    private static final long serialVersionUID = 8400479582573245661L;
	
	public UnsuportedDateFormatException(String field) {
		super("The follow field is not suported: " + field);
	}
}

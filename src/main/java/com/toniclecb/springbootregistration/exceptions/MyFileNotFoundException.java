package com.toniclecb.springbootregistration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MyFileNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public MyFileNotFoundException(String ex) {
		super(ex);
	}
	
	public MyFileNotFoundException(String ex, Throwable cause) {
		super(ex, cause);
	}
}

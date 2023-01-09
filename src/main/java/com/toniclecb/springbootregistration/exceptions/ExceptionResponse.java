package com.toniclecb.springbootregistration.exceptions;

import java.io.Serializable;
import java.util.Date;

/**
 * Used to return to the client information about the exception to client
 */
public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 9100279187579243662L;

    private Date timestamp;
	private String message;
	private String details;
	
	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}

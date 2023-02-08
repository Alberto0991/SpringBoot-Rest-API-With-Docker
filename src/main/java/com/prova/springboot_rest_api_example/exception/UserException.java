package com.prova.springboot_rest_api_example.exception;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 2813199339868959437L;

	public UserException() {
		super();
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(Throwable cause) {
		super(cause);
	}
}

package com.prova.springboot_rest_api_example.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(Class<?> clazz) {
		super("No " + clazz.getSimpleName() + " was found");
	}

	public NotFoundException(Class<?> clazz, Object entityId) {
		super("No " + clazz.getSimpleName() + " was found with ID " + entityId);
	}

	public NotFoundException(Class<?> clazz, String message, Object entityId) {
		super("No " + clazz.getSimpleName() + " was found with " + message + " " + entityId);
	}

}

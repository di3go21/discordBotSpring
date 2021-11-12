package com.discbotback.upload;

public class StorageException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StorageException(String mensaje) {
		super(mensaje);
	}

	public StorageException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
}

package com.devsuperior.dsclients.service.exception;

public class ClientDataBaseExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ClientDataBaseExistsException(String msg) {
		super(msg);
	}

}

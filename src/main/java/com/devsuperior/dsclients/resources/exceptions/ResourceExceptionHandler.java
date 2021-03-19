package com.devsuperior.dsclients.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dsclients.builder.StandardErrorBuilder;
import com.devsuperior.dsclients.service.exception.ClientDataBaseExistsException;
import com.devsuperior.dsclients.service.exception.DataBaseException;
import com.devsuperior.dsclients.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status)
	             .body(new StandardErrorBuilder()
	            		   .exception(e)
	            		   .request(request)
	            		   .status(status)
	            		   .message("Resource not found")
	            		   .builder());
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status)
				             .body(new StandardErrorBuilder()
				            		   .exception(e)
				            		   .request(request)
				            		   .status(status)
				            		   .message("Database exception")
				            		   .builder());
	}
	
	@ExceptionHandler(ClientDataBaseExistsException.class)
	public ResponseEntity<StandardError> dataBaseintegridade(ClientDataBaseExistsException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status)
				             .body(new StandardErrorBuilder()
				            		   .exception(e)
				            		   .request(request)
				            		   .status(status)
				            		   .message("Database integridade exception")
				            		   .builder());
	}

}

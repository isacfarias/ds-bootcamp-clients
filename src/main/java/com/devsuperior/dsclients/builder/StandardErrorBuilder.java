package com.devsuperior.dsclients.builder;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.devsuperior.dsclients.resources.exceptions.StandardError;

public class StandardErrorBuilder {
	
	StandardError error;
	
	public StandardErrorBuilder() {
		this.error = new StandardError();
		this.error.setTimestamp(Instant.now());
	}
	
	public StandardErrorBuilder exception(Exception e) {
		this.error.setMessage(e.getMessage());
		return this;
	}
	
	public StandardErrorBuilder request(HttpServletRequest request) {
		this.error.setPath(request.getRequestURI());
		return this;
	}
	
	public StandardErrorBuilder status(HttpStatus status) {
		this.error.setStatus(status.value());
		return this;
	}
	
	public StandardErrorBuilder message(String msg) {
		this.error.setError(msg);
		return this;
	}
	
	public StandardError builder() {
		return error;
	}
}

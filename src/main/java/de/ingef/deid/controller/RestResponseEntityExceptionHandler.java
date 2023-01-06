package de.ingef.deid.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(NOT_FOUND)
	public ResponseEntity<String>  handleNotFoundException(NoSuchElementException e) {
		return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
	}
}

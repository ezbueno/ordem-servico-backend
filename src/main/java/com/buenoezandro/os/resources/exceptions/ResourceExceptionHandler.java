package com.buenoezandro.os.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.buenoezandro.os.services.exceptions.DataIntegrityViolationException;
import com.buenoezandro.os.services.exceptions.ObjectNotFoundException;
import com.buenoezandro.os.services.exceptions.IllegalArgumentException;
import com.buenoezandro.os.utils.MensagemUtils;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	private ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
		var error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	private ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException e) {
		var error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e) {
		var error = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				MensagemUtils.CAMPO_INVALIDO);

		for (FieldError err : e.getBindingResult().getFieldErrors()) {
			error.addErrors(err.getField(), err.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e) {
		var error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}

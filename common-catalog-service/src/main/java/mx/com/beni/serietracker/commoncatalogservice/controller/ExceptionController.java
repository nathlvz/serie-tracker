package mx.com.beni.serietracker.commoncatalogservice.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mx.com.beni.serietracker.commoncatalogservice.exception.NotDeletedException;
import mx.com.beni.serietracker.commoncatalogservice.exception.NotFoundException;
import mx.com.beni.serietracker.commoncatalogservice.exception.NotUpdatedException;
import mx.com.beni.serietracker.commoncatalogservice.exception.UnregisteredRecordException;
import mx.com.beni.serietracker.commoncatalogservice.model.InformationError;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	// @formatter:off
	@ExceptionHandler(value = { 
			NotDeletedException.class, 
			NotUpdatedException.class,
			UnregisteredRecordException.class 
	})
	public ResponseEntity<InformationError> handleNotDeletedException(Throwable exception) {
		return ResponseEntity.internalServerError()
				.body(new InformationError(exception.getMessage(), Collections.emptyList()));
	}
	// @formatter:on
	
	

	// @formatter:off
	@ExceptionHandler(value = { NotFoundException.class })
	public ResponseEntity<InformationError> handleNotFoundException(NotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new InformationError(exception.getMessage(), Collections.emptyList()));
	}
	// @formatter:on

}

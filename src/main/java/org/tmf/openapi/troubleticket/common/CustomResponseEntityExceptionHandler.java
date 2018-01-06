package org.tmf.openapi.troubleticket.common;

import java.util.Date;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Provides handling for Bad Requests exceptions.
	 * 
	 * @param ex
	 *            the target exception
	 * @param request
	 *            the current request
	 */
	@ExceptionHandler({ RollbackException.class, ConstraintViolationException.class, IllegalArgumentException.class })
	@Nullable
	public final ResponseEntity<CustomExceptionResponse> handleConstraintViolationException(Exception ex,
			WebRequest request) {

		CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();

		Throwable cause = ExceptionUtils.getRootCause(ex);
		if (null == cause) {
			cause = ex;
		}

		exceptionResponse.setTimestamp(new Date());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
		exceptionResponse.setError("Bad Request");
		exceptionResponse.setMessage(cause.getMessage());
		exceptionResponse.setPath(request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

	}
}

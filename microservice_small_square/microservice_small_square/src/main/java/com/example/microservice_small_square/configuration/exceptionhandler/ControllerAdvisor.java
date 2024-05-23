package com.example.microservice_small_square.configuration.exceptionhandler;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PendingOrderExistsException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.ValueAlreadyExitsException;
import com.example.microservice_small_square.domain.exception.SizeMinException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    private String getErrorMessage(FieldError error) {
        if (error == null) {
            return "Validation error";
        }
        String code = error.getCode();
        switch (code != null ? code : "") {
            case "NotBlank":
                return Constants.EMPTY_FIELD_EXCEPTION_MESSAGE;
            case "Size":
                return Constants.MAX_CHAR_EXCEPTION_MESSAGE;
            default:
                return error.getDefaultMessage();
        }

    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
            BindingResult result = ex.getBindingResult();
            FieldError error = result.getFieldError();
            String errorMessage = getErrorMessage(error);
            return ResponseEntity.badRequest().body(new ExceptionResponse(
                    errorMessage, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
        }

        @ExceptionHandler(DataNotFoundException.class)
        public ResponseEntity<ExceptionResponse> handleDataNotFoundException(DataNotFoundException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(
                    ex.getMessage() + Constants.DATA_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
        }

        @ExceptionHandler(SizeMinException.class)
        public ResponseEntity<ExceptionResponse> handleSizeMinException(SizeMinException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(
                    ex.getMessage() + Constants.SIZE_MIN_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
        }

        @ExceptionHandler(PermissionDeniedException.class)
        public ResponseEntity<ExceptionResponse> handlePermissionDeniedException(PermissionDeniedException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(
                    ex.getMessage() + Constants.PERMISSION_DENIED_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
        }

        @ExceptionHandler(ValueAlreadyExitsException.class)
        public ResponseEntity<ExceptionResponse> handleValueAlreadyExitsException(ValueAlreadyExitsException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(
                    ex.getMessage() + Constants.VALUE_ALREADY_EXISTS_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
        }

        @ExceptionHandler(PendingOrderExistsException.class)
        public ResponseEntity<ExceptionResponse> handlePendingOrderExistsException(PendingOrderExistsException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.PENDING_STATUS_EXCEPTION_MESSAGE,
                    HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
        }

    }



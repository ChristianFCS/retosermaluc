package com.ccondori.retosermaluc.commons.controllers;

import com.ccondori.retosermaluc.commons.exceptions.BusinessException;
import com.ccondori.retosermaluc.commons.models.ResponseApi;
import com.ccondori.retosermaluc.commons.models.ResponseApiPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(BusinessException.class)
    public ResponseApi<?> businessError(BusinessException be) {

        ResponseApi<?> responseApi = new ResponseApi<>();

        responseApi.setPayload(
                (new ResponseApiPayload<>(false, be.getMessage(), null))
        );
        return responseApi;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing));

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> responseFileError(ResponseStatusException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("message", "Hubo un problema con el archivo.("+e.getMessage()+")");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseApi<?> exceptionError(Exception e) {
        return (new ResponseApi<>()).responseError("Error en el proceso.", e.getMessage());
    }
}

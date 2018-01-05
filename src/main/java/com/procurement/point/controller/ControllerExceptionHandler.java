package com.procurement.point.controller;

import com.procurement.point.exception.GetDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(GetDataException.class)
    public ResponseEntity<String> handleErrorInsertException(final GetDataException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

package com.abn_amro.exercise.handler;

import com.abn_amro.exercise.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Exception mechanism to display a proper message to the clients.
 */
@RestControllerAdvice
public class ControllerAdviceHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResponse processRuntime(Exception ex) {
        MessageResponse response = new MessageResponse();
        response.setDate(LocalDateTime.now());
        response.setText(ex.getMessage());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return response;
    }
}

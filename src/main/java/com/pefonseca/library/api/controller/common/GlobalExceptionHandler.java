package com.pefonseca.library.api.controller.common;

import com.pefonseca.library.api.controller.dto.ErrorProperty;
import com.pefonseca.library.api.controller.dto.ErrorResponse;
import com.pefonseca.library.api.exceptions.DuplicateRecordException;
import com.pefonseca.library.api.exceptions.OperationNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<ErrorProperty> listErrors = fieldErrors
                .stream()
                .map(fe -> new ErrorProperty(fe.getField(), fe.getDefaultMessage()))
                .toList();
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", listErrors);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateRecordException(DuplicateRecordException ex) {
        return ErrorResponse.responseConflict(ex.getMessage());
    }

    @ExceptionHandler(OperationNotPermitted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOperationNotPermittedException(OperationNotPermitted ex) {
        return ErrorResponse.responseDefault(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleExceptionGeneric(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com a adminstração.",
                List.of());
    }

}
package com.example.mozi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = MoziRestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacio(MethodArgumentNotValidException ex) {
        Map<String, String> mezok = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> mezok.put(e.getField(), e.getDefaultMessage()));
        Map<String, Object> valasz = new LinkedHashMap<>();
        valasz.put("hiba", "Érvénytelen adatok");
        valasz.put("mezok", mezok);
        return ResponseEntity.badRequest().body(valasz);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> hibasJson(HttpMessageNotReadableException ex) {
        Map<String, Object> valasz = new LinkedHashMap<>();
        valasz.put("hiba", "Hibás vagy hiányzó JSON a kérés törzsében");
        return ResponseEntity.badRequest().body(valasz);
    }
}
package com.proyecto.tienda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotación le dice a Spring que devuelva automáticamente un código 404 si la excepción no se captura
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEncontradoException extends RuntimeException {
    
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

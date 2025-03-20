package com.daicode.authscopes.controller;

import com.daicode.authscopes.config.headers.HeaderAuth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * LOS MÉTODOS CON LA ANOTACIÓN DE AUTH ESPERAN LOS PARÁMETROS QUE EL DECORADOR TENGA,
 * PERO SI NO TIENE DECORADOR, DEBE EJECUTARSE SIN PROBLEMA
 *
 * */

@RestController
@RequestMapping("${controller.properties.base-path}/method-level")
public class MethodLevelController {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @HeaderAuth(scopes = {"WRITE", "READ"})
    @GetMapping("/scopes/hello")
    public String getGreetingWithScope() {
        return """
                Hello with scopes.
                Executor scopes: WRITE or READ.
                Decorator at: method level.""";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/no-decorator/hi")
    public String getGreeting() {
        return """
                Hi without decorator!!!.
                Executor scope: no scopes.
                Decorator at: no decorator.""";
    }
}
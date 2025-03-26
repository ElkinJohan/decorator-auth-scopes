package com.daicode.authscopes.controller;

import com.daicode.authscopes.config.headers.HeaderAuth;
import com.daicode.authscopes.dto.ApiResponseDTO;
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
    public ApiResponseDTO getGreetingWithScope() {
        return ApiResponseDTO.builder()
                .message("Hello with scopes.")
                .executorScope("WRITE or READ")
                .decoratorAt("method level")
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/no-decorator/hi")
    public ApiResponseDTO getGreeting() {
        return ApiResponseDTO.builder()
                .message("Hi without decorator!!!")
                .executorScope("no scopes")
                .decoratorAt("no decorator")
                .build();
    }
}
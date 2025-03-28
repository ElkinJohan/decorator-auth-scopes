package com.daicode.authscopes.controller;

import com.daicode.authscopes.config.headers.HeaderAuth;
import com.daicode.authscopes.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * TODOS LOS MÉTODOS DE ESTA CLASE NECESITAN PASAR POR EL AUTH DE SCOPES
 *
 * */
@HeaderAuth(scopes = {"BOSS"})
@RestController
@RequestMapping("${controller.properties.base-path}/class-level")
public class ClassLevelController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-with-scopes/greeting")
    public ApiResponseDTO greetBoss() {
        return ApiResponseDTO.builder()
                .message("Greetings!!!")
                .executorScope("BOSS")
                .decoratorAt("class level")
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-with-scopes/bye")
    public ApiResponseDTO sayGoodbyeBoss() {
        return ApiResponseDTO.builder()
                .message("Goodbye!!!")
                .executorScope("BOSS")
                .decoratorAt("class level")
                .build();
    }
}

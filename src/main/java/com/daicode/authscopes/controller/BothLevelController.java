package com.daicode.authscopes.controller;

import com.daicode.authscopes.config.headers.HeaderAuth;
import com.daicode.authscopes.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
 * EL DECORADOR EN CLASE APLICA A TODOS LOS MÉTODOS EXISTENTES,
 * PERO SI EL MÉTODO TIENE DECORADOR PROPIO, SOBREESCRIBE EL MÉTODO DE CLASE PARA ESE MÉTODO.
 *
 * */
@HeaderAuth(scopes = {"greeting"})
@RestController
@RequestMapping("${controller.properties.base-path}/both")
public class BothLevelController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/class-level")
    public ApiResponseDTO getGreetingClassLevel() {
        return ApiResponseDTO.builder()
                .message("Both level: hello.")
                .executorScope("greeting")
                .decoratorAt("class level")
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @HeaderAuth(scopes = {"goodbye"})
    @PostMapping("/method-level-in-class-level")
    public ApiResponseDTO postAdminData() {
        return ApiResponseDTO.builder()
                .message("Both level: bye!!")
                .executorScope("goodbye")
                .decoratorAt("method level")
                .build();
    }
}

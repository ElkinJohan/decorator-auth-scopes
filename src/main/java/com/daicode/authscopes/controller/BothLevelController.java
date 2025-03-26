package com.daicode.authscopes.controller;

import com.daicode.authscopes.config.headers.HeaderAuth;
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
    public String getGreetingClassLevel() {
        return """
                Both level: hello.
                Executor scope: greeting.
                Decorator at: class level
                """;
    }

    @ResponseStatus(HttpStatus.OK)
    @HeaderAuth(scopes = {"goodbye"})
    @PostMapping("/method-level-in-class-level")
    public String postAdminData() {
        return """
                Both level: bye!!
                Executor scope: goodbye.
                Decorator at: method level
                """;
    }
}

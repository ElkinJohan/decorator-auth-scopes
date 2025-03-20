package com.daicode.authscopes.controller;

import com.daicode.authscopes.config.headers.HeaderAuth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * TODOS LOS MÉTODOS DE ESTA CLASE NECESITAN PASAR POR EL AUTH DE SCOPES
 *
 * */
@HeaderAuth(scopes = {"greeting", "goodbye"})
@RestController
@RequestMapping("${controller.properties.base-path}/class-level")
public class ClassLevelController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-with-scopes/greeting")
    public String getGreeting() {
        return """
                Greetings!!!.
                Executor scope: greeting.
                Decorator at: class level.""";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-with-scopes/bye")
    public String sayGoodbye() {
        return """
                Goodbye!!!.
                Executor scope: goodbye.
                Decorator at: class level.""";
    }
}

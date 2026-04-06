
package com.example.demo.controller;
import com.example.demo.service.GreetingService;
import com.example.demo.service.GreetingService_0;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingController {

    private final GreetingService greetingService;
    private final GreetingService_0 greetingService_0;

    public GreetingController(GreetingService greetingService, GreetingService_0 greetingService_0) {
        this.greetingService = greetingService;
        this.greetingService_0 = greetingService_0;
    }

    @GetMapping("/greet-no-di")
    public String greetNoDI(){
        return greetingService_0.getGreeting();
    }

    @GetMapping("/greet")
    public String greet() {
        return greetingService.getGreeting();
    }

    @GetMapping("/greet-config")
    public String greetFromConfig(){
        return greetingService.getConfigGreeting();
    }

    @GetMapping("/greetuser")
    public String greet(@RequestParam(defaultValue = "User") String name) {
        return "Hello, " + name;
    }
}


/*
./gradlew bootRun

curl.exe http://localhost:8080/greet
curl.exe http://localhost:8080/greet-no-di
curl.exe http://localhost:8080/greetuser?name=eefei

curl.exe http://localhost:8081/oishi/greet
curl.exe http://localhost:8081/oishi/greet-no-di
curl.exe http://localhost:8081/oishi/greetuser?name=eefei
curl.exe http://localhost:8081/oishi/greet-config

*/

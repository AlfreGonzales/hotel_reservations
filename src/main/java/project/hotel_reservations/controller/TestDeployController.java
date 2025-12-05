package project.hotel_reservations.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDeployController {
    @GetMapping("/test")
    public String testEndpoint() {
        return "Hello World from TestController!";
    }
}

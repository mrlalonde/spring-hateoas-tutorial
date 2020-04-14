package com.github.mrlalonde.hateoas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class HelloController {
    private final String helloMsg;
    private final String byeMsg;

    HelloController(@Value("${helloMsg}") String helloMsg,
                    @Value("${byeMsg:BYE}") String byeMsg) {
        this.helloMsg = helloMsg;
        this.byeMsg = byeMsg;
    }

    @GetMapping("/hello")
    String hello() {
        return helloMsg;
    }

    @GetMapping("/bye")
    String bye() {
        return byeMsg;
    }
}

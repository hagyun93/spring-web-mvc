package me.whiteship;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/sample/hello/{name}")
    public String hello(
            @PathVariable("name") Person person,
            @RequestParam(value = "name", required = false) Person person1
    ) {
        return "hello " + person.getName() + " " + person1.getName();
    }
}

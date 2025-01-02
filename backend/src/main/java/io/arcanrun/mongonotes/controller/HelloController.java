package io.arcanrun.mongonotes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hello controller")
@RestController
@RequestMapping("/hello")
public class HelloController {

  @GetMapping
  public String sayHello() {
    return "Hello world!";
  }
}

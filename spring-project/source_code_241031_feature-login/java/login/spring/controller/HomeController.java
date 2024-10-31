package login.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        System.out.println(getClass() + "   home() home.html callback");
        return "home";
    }
}

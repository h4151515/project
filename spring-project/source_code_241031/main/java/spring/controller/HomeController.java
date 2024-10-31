package hello_v3.hello.v3.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    /*
        1. 톰캣에서 controller 먼저 찾음
        2. 없으면 static 폴더 검색
     */
    @GetMapping("/")
    public String home() {
        System.out.println(getClass() + "   home() ");
        return "home";
    }

}

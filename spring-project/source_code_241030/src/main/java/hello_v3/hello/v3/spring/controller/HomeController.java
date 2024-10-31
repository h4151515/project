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
        System.out.println(getClass() + "   /root 위치로 이동합니다 (home.html로 찾아가기)");
        return "home";
    }

}

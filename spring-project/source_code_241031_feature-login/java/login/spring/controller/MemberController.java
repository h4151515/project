package login.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/add")
    public String addUser() {
        System.out.println(getClass() + "   addUser() process completed");
        return "/user/addMemberForm";
    }
}

package hello_v3.hello.v3.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/members")
    public String createMember() {
        System.out.println(getClass() + "   /members/new 위치로 이동합니다 (createMemberForm.html로 찾아가기)");
        return "createMemberForm";
    }
}

package login.spring.controller;

import login.spring.domain.EzenMember;
import login.spring.dto.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginContoller {

//    @GetMapping("/login")
//    public String login() {
//        System.out.println(getClass() + "   login() process completed");
//        return "/user/loginForm";
//    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new MemberForm());
        return "user/loginForm";
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute("loginForm") MemberForm form) {
//        EzenMember member = new EzenMember();
//        member.setLoginId(form.getLoginId());
//        member.setName(form.getName());
//        member.setPassword(form.getPassword());
//        member.setGrade("user");
//        System.out.println(getClass() + "   createMember() userInfo (" + member.getLoginId() + ", " + member.getName() + ")");
//        memberService.join(member);
//        System.out.println(getClass() + "   createMember() process complete");
//        return "redirect:/";
//    }


}

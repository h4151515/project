package login.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import login.spring.domain.EzenMember;
import login.spring.dto.LoginForm;
import login.spring.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginContoller {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "user/loginForm";
    }

    /*
        로그인 성공 시 세션이 있으면 세션 반환, 없으면 신규 세션 생성
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletRequest req) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }
        EzenMember loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login() userInfo (" + form.getLoginId() + ", " + form.getPassword() + ")");
        if(loginMember == null){
            bindingResult.reject("login()   loginFail", "아이디 또는 패스워드가 맞지 않습니다");
            return "user/loginForm";
        }

        HttpSession session = req.getSession();
        log.info("login() , {session info : " + session.getId() + "}");
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // httpSession 데이터를 보관, 조회 할때 같은 이름이 중복으로 사용 되므로 상수를 정의 해 사용함 (보통은 enum으로 정의)
        log.info("login() , loginService.login() Return Value " + loginMember.toString());
        log.info("login() process complete");

        return "redirect:/";
    }

    /*
        로그아웃 (세션을 이용한 로그아웃)
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);// 세션이 없는 경우 새로 만들기 때문에 false로 가지고 옮
        if(session!=null){
            session.invalidate(); // 세션 제거 메서드
            log.info("logout() , session info : " + session.getId());
        }
        log.info("logout() process complete");
        return "redirect:/";
    }
}

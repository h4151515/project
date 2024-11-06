package login.spring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

@RequiredArgsConstructor
@Slf4j
public class LoginContoller_cookie {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "user/loginForm";
    }

    /*
        session 유지 (HttpServletRequest 사용)
        쿠키에 시간 정보를 넣지 않으면 세션 쿠키는 브라우저 종료 시 모두 종료
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletResponse resp) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }
        EzenMember loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login() userInfo (" + form.getLoginId() + ", " + form.getPassword() + ")");
        if(loginMember == null){
            bindingResult.reject("login()   loginFail", "아이디 또는 패스워드가 맞지 않습니다");
            return "user/loginForm";
        }
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        resp.addCookie(idCookie); // id 값을 Set-id에 넣어서 사용함 (--> Set-Cookie: memberId=7)

        log.info("login() , loginService.login() Return Value " + loginMember.toString());
        log.info("login() process complete");
        return "redirect:/";
    }

    /*
        로그아웃 (쿠키를 이용한 로그아웃)
     */
    @PostMapping("/logout")
    public String logout(HttpServletResponse resp) {
        expireCookie(resp, "memberId");
        log.info("logout() process complete");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse resp, String memberId) {
        Cookie cookie = new Cookie(memberId, null);
        cookie.setMaxAge(0); // 쿠키시간을 0으로 만듦
        log.info("expireCookie() exchange info {cookie value : " + cookie.getValue() + "}");
        resp.addCookie(cookie);
    }
}

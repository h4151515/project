package login.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import login.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberRepository memberRepository;

    /*
        쿠키 대신 세션 정보를 이용해 로그인/로그아웃 상태 확인
     */
    @GetMapping("/")
    public String home(HttpServletRequest req, Model model) {

        HttpSession session = req.getSession(false); // false 를 안하면 세션을 가져 올때 기본값이 true 이어서 생성 해 버림
        if(session == null) {
            log.info("home() not login status, callback");
            return "home";    
        }
        // 로그인 시점에 세션에 보관한 회원 객체를 찾음
        Object loginMember = session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 home 으로 리턴
        if(loginMember==null) {
            return "home";
        }

        // 세션이 유지 되면 로그인으로 이동
        Model member = model.addAttribute("loginMember", loginMember);
        log.info("loginHome() login status check, user-info " + member.toString());
        log.info("loginHome() login status check, session-info {sessionId : " + session.getId() + "}");

        return "loginHome";
    }
}
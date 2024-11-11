package login.spring.controller;

import login.spring.domain.EzenMember;
import login.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class HomeController_cookie {

    private final MemberRepository memberRepository;

    /*
        세션 쿠키 만들기
        종료는 쿠키의 날짜를 0으로 변경 해서 로그 아웃
     */
    @GetMapping("/")
    public String home(@CookieValue(name="memberId", required = false)Long memberId, Model model) {
        if(memberId == null) {
            log.info("home() not login status, callback");
            return "home";    
        }
        Optional<EzenMember> loginMember = memberRepository.findById(memberId);
        if(loginMember.isPresent()) {
            EzenMember findLoginMember = loginMember.get();
            model.addAttribute("loginMember", findLoginMember);
            log.info("loginHome() login status check, cookie-value {memeberId : " + findLoginMember.getId() + "}");
            return "loginHome";
        }
        return "home";
    }
}

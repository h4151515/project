package hello_v3.hello.v3.spring.controller;

import hello_v3.hello.v3.spring.domain.Member;
import hello_v3.hello.v3.spring.dto.MemberForm;
import hello_v3.hello.v3.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm() {
        System.out.println(getClass() + "   createForm() ");
        return "members/createMemberForm";
    }

    /*
        html - (MemberForm[DTO]생성) Controller --> (Member생성)Service --> Repository
        pre) MemberService를 사용 하기 위해 private final로 필드 생성 후 @RequiredArgsConstructor 로 생성자 어노테이션 추가
        1. 컨트롤러단에서 DTO를 받는 형태로 처리 하고
        2. create 메소드 안에서 Member 생성
        3. Member 객체 안에 있는 내용을 MemberForm 안에 주입
        4. 서비스단으로 세이브 메서드 요청
     */
    @PostMapping("/members/new")
    public String createMember(MemberForm form) {
        Member member = new Member();
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        System.out.println(getClass() + "  createMember()");
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String listMembers(Model model) {
        List<Member> members = memberService.findByAllMember();
        model.addAttribute("members", members);
        System.out.println(getClass() + "    listMembers() ");
        System.out.println("                             전체가입자수 : " + members.size());
        return "members/memberList";
    }

    @GetMapping("/members/{memberid}/delete")
    public String deleteMember(@PathVariable("memberid") Long memberid) {
        Optional<Member> findMember = memberService.findByMemeber(memberid);
        if(findMember.isPresent()) {
            memberService.deleteByMember(findMember.get());
        }

        return "redirect:/members";
    }
}

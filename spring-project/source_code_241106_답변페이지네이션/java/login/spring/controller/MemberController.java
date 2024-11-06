package login.spring.controller;

import jakarta.validation.Valid;
import login.spring.domain.EzenMember;
import login.spring.dto.MemberForm;
import login.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
/*
    컨트롤러 기본 동작
    - 뷰단 (웹)에서 /members를 호출 하면 컨트롤러에 매핑된 path와 http method를 보고 controller에서 사용할 method를 선택함
    - return 에 보낼 path를 넣어서 넘기면, template 안에 있는 위치에 있는 html 파일명과 동일한 값으로 뷰리졸브를 호출
 */
public class MemberController {

    private final MemberService memberService;

    /*
    최초 작성 된 뷰페이지 이동을 위한 createMember() 메서드
    DTO의 MemberForm 객체에 속성 값을 부여 해서, 뷰단에서 Thymeleaf를 이용해
    입력된 필드값들을 가져오는 메서드로 리팩토링 함

    @GetMapping("/add")
    public String createMember() {
        log.info("   createMember() process completed");
        return "/user/addMemberForm";
    }
     */

    /*
       createMember() 메서드에서 Model 클래스를 이용해 DTO의 각 컬럼을 접근 하여 Getter/Setter 사용을 하게함
       model.addAttribute 를 이용해, 뷰에서 넘겨 받을 값을 저장할 빈인 DTO 객체를 생성(Set)
     */
    @GetMapping("/add")
    public String createMember(Model model){
        model.addAttribute("memberForm", new MemberForm());
        log.info("createMember() process completed");
        return "user/addMemberForm";
    }

    @PostMapping("/add") // 뷰단에서 작성된 데이터를 받아 올때, @ModelAttribute를 이용해 가져옴
    public String createMember(@Valid @ModelAttribute("memberForm") MemberForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "user/addMemberForm";
        }

        EzenMember member = new EzenMember();
        member.setLoginId(form.getLoginId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setGrade("user"); // 제대로 안되면 "user"
        log.info("createMember() userInfo (" + member.getLoginId() + ", " + member.getName() + ")");
        memberService.join(member);
        log.info("createMember() process complete");
        return "redirect:/";
    }

    /*
        Model 클래스를 이용해 뷰로 넘길 객체를 선언
        MemberService 클래스에서 조회한 회원 정보를 List에 담음
        model 객체에 List로 만든 members를 속성값으로 부를수 있게 추가 후 뷰단으로 리턴
     */
    @GetMapping("/members")
    public String list(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required=true) EzenMember loginMember, Model model) { // required false로 지정
        List<EzenMember> members = memberService.findByAllMember();
        model.addAttribute("memberList", members);
        model.addAttribute("loginMember", loginMember); // 세션 정보를 가져 오기 위함 241104
        log.info("getMemberList() ");
        log.info("Total Member Count : " + members.size());
        return "admin/memberList";
    }

    @GetMapping("/members/{id}/delete") // 뷰단에서 사용한 {id}와 반드시 같을 필요는 없음
    public String deleteMember(@PathVariable("id") Long memberid) {
        Optional<EzenMember> findMember = memberService.findByMember(memberid);
        if(findMember.isPresent()) {
            log.info("deleteMember() delete Info : " + findMember.toString());
            memberService.deleteMember(findMember.get());
        }
        log.info("deleteMember() process complete");
        return "redirect:/members";
    }

    /*  
        백단에서 뷰단으로 뿌려주는 로직
        updateMemberForm을 뷰단에 뿌려주는 로직을 구성
        id 값을 받고, 뿌려줄 model 객체를 생성

     */
    @GetMapping("/members/{id}/edit")
    public String updateMember(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required=true)EzenMember loginMember, @PathVariable("id") Long id, Model model) {
        Optional<EzenMember> editMember = memberService.findByMember(id);
        EzenMember editMemberGet = editMember.get();

        // 뷰단에 뿌러주기 위해서 MemberForm 객체를 생성 하고, 그 안에 Optinal 안에서 가져온 데이터를 memberForm안에 set 하는 로직
        MemberForm memberForm = new MemberForm();

        memberForm.setId(editMemberGet.getId());
        memberForm.setLoginId(editMemberGet.getLoginId());
        memberForm.setName(editMemberGet.getName());
        memberForm.setPassword(editMemberGet.getPassword());
        memberForm.setGrade(editMemberGet.getGrade());
        
        // memberForm에 넣은 값을 뷰(UI)에 그려줄 model 객체를 주입
        model.addAttribute("memberForm", memberForm);
        model.addAttribute("loginMember", loginMember); // 세션 정보를 가져 오기 위함 241104
        log.info("updateMember() process complete");
        
        return "/admin/updateMemberForm";
    }

    /*
        메소드 이름이 같아도 HTTP 메서드와 매개변수의 타입/수에 따라 오버로딩이 가능하므로, 위 코드와 같이 사용하는 것은 전혀 문제 없음
        앞에 작성된 PostMapping 부분 참고
     */
    @PostMapping("/members/{id}/edit")
    public String updateMember(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required=true)EzenMember loginMember, @Valid @ModelAttribute("memberForm") MemberForm form, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/admin/updateMemberForm";
        }
        model.addAttribute("loginMember", loginMember); // 세션 정보를 가져 오기 위함 241104

        EzenMember member = new EzenMember();

        member.setId(form.getId());
        member.setLoginId(form.getLoginId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setGrade(form.getGrade());

        log.info("updateMember() userInfo (" + member.getLoginId() + ", " + member.getName() + ", [id:" + member.getId() + "])");
        memberService.save(member);
        log.info("updateMember() process complete");
        return "redirect:/members";
    }
}
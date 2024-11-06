package login.spring.controller;

import jakarta.validation.Valid;
import login.spring.domain.EzenMember;
import login.spring.domain.Question;
import login.spring.dto.QuestionForm;
import login.spring.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    /*
        (@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = true) EzenMember loginMember
        일때, required = true 없어도 기본값으로 true 적용
        아니면 required = false (e.g. 테스트 할때 사용)
     */

    @GetMapping("/question/create")
    public String getCreateQuestion(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model) {
        model.addAttribute("questionForm", new QuestionForm());
        model.addAttribute("loginMember", loginMember);
        log.info("getCreateQuestion() process complete, return to /user/questionForm");
        return "/user/questionForm";
    }

    @PostMapping("/question/create")
    public String postCreateQuestion(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @Valid @ModelAttribute("questionForm") QuestionForm questionForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            log.info("postCreateQuestion() has error for no-data");
            return "/user/questionForm";
        }
        model.addAttribute("loginMember", loginMember);

        Question question = new Question();
        /*
            loginMember 객체를 받아서 넣어도, 나중에 Entity에서 받을때 author를 member_id로 받겠다고 정의 했기 때문에 domain에서 저장 할때, member_id 만 가지고 오게 됨
         */
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(loginMember);

        log.info("postCreateQuestion() formDataInfo {subject : " + questionForm.getSubject() + ", content : " + questionForm.getContent() + ", memberId : " + loginMember + "}");
        questionService.createQuestion(question);
        model.addAttribute("loginMember", loginMember);
        log.info("postCreateQuestion() process complete, return to /question/list");

        return "redirect:/question/list";
    }

    @GetMapping("/question/list")
    public String getQuestionList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, Model model) {
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        model.addAttribute("loginMember", loginMember);
        log.info("getQuestionList() process complete");
        log.info("Total Question Count : " + questionList.size());
        return "/user/questionList";
    }

    @GetMapping("/question/detail/{id}")
    public String getQuestionListDetail(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @PathVariable("id") Long id, Model model) {
        Optional<Question> detailQuestion = questionService.getListById(id);
        if(detailQuestion.isPresent()){
            Question viewQuestion = detailQuestion.get();

            Question questionForm = new Question();

            questionForm.setSubject(viewQuestion.getSubject());
            questionForm.setContent(viewQuestion.getContent());
            questionForm.setAuthor(viewQuestion.getAuthor());
            questionForm.setCreateDate(viewQuestion.getCreateDate());

            model.addAttribute("questionForm", questionForm); // viewQuestion.get()
            model.addAttribute("loginMember", loginMember);
            log.info("getQuestionDetail() process complete");
            return "/user/questionDetail";
        }

        model.addAttribute("loginMember", loginMember);
        log.info("getQuestionDetail() process complete , search detail is not found");
        return "/user/questionList";
    }
}

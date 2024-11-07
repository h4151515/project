package login.spring.controller;

import jakarta.validation.Valid;
import login.spring.domain.Answer;
import login.spring.domain.EzenMember;
import login.spring.domain.Question;
import login.spring.dto.AnswerForm;
import login.spring.dto.QuestionForm;
import login.spring.service.AnswerService;
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
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/answer/create/{aid}") // 질문에 대한 답변하기
    public String postCreateAnswer(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @Valid @ModelAttribute("answerForm") AnswerForm answerForm, BindingResult bindingResult, Model model, @PathVariable("aid") Long aid) {
        Optional<Question> getQuestion = questionService.getListById(aid);
        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("question", getQuestion.get());
            return "/user/questionDetail";
        }

        if(getQuestion.isPresent()){
            Answer answer = new Answer();
            answer.setContent(answerForm.getContent());
            answer.setCreateDate(LocalDateTime.now());
            answer.setQuestion(getQuestion.get());
            answer.setAuthor(loginMember);
            answerService.createAnswer(answer);
        }
        model.addAttribute("loginMember", loginMember);
        return String.format("redirect:/question/detail/%s",aid);
    }

    @GetMapping("/answer/modify/{aid}") // 답변 수정 화면 불러오기
    public String getModifyAnswer(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @PathVariable("aid") Long aid, AnswerForm answerForm, Model model) {
        Optional<Answer> answer = answerService.getAnswerListById(aid);

        if(answer.isPresent()) {
            answerForm.setContent(answer.get().getContent());
            model.addAttribute("answerForm", answerForm);
            model.addAttribute("loginMember", loginMember);
            return "/user/answerForm";
        } else {
            log.info("축 에러");
        }
        return "/user/questionList";
    }

    @PostMapping("/answer/modify/{aid}") // 답변 수정 저장 하기
    public String postModifyAnswer(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @PathVariable("aid") Long aid, @Valid @ModelAttribute("answerForm") AnswerForm answerForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            log.info("postModifyAnswer() value is not exist");
            return "/user/answerForm";
        }
        log.info("postModifyAnswer() question object called from view, keep the next process");
        Optional<Answer> findAnswer = answerService.getAnswerListById(aid); // answerId로 answer 데이터 가지고 오기
        Optional<Question> findQuestion = questionService.getListById(findAnswer.get().getQuestion().getId()); // questionId 가져오기

        Answer answer = new Answer();
        answer.setId(aid);
        answer.setContent(answerForm.getContent());
        answer.setCreateDate(findAnswer.get().getCreateDate());
        answer.setModifyDate(LocalDateTime.now());
        answer.setAuthor(loginMember);
        answer.setQuestion(findQuestion.get());

        answerService.modifyAnswer(answer);
        model.addAttribute("loginMember", loginMember);
        log.info("postModifyAnswer() process complete");

        return String.format("redirect:/question/detail/%s", findAnswer.get().getQuestion().getId());
        }

    @GetMapping("/answer/delete/{aid}") // 질문 삭제 하기
    public String getDeleteAnswer(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @PathVariable("aid") Long aid, Model model) {

        Optional<Answer> findAnswer = answerService.getAnswerListById(aid); // answerId로 answer 데이터 가지고 오기
        Optional<Question> findQuestion = questionService.getListById(findAnswer.get().getQuestion().getId()); // questionId 가져오기
        log.info("getDeleteAnswer() findAnser<> : " + findAnswer + ", findQuestion<> : " + findQuestion);

        model.addAttribute("loginMember", loginMember);
        log.info("getDeleteAnswer() data deletion finish, Answer Id : " + aid + ", Question Id : " + findAnswer.get().getQuestion().getId());
        return String.format("redirect:/question/detail/%s", findAnswer.get().getQuestion().getId());
    }

}

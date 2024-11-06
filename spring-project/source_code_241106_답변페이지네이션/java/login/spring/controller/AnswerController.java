package login.spring.controller;

import jakarta.validation.Valid;
import login.spring.domain.Answer;
import login.spring.domain.EzenMember;
import login.spring.domain.Question;
import login.spring.dto.AnswerForm;
import login.spring.service.AnswerService;
import login.spring.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/answer/create/{id}") // 질문에 대한 답변하기
    public String postCreateAnswer(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @Valid @ModelAttribute("answerForm") AnswerForm answerForm, BindingResult bindingResult, Model model, @PathVariable("id") Long id) {
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%% gogo");
        Optional<Question> getQuestion = questionService.getListById(id);
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
        return String.format("redirect:/question/detail/%s",id);
    }

}

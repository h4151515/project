package login.spring.controller;

import jakarta.validation.Valid;
import login.spring.domain.EzenMember;
import login.spring.domain.Question;
import login.spring.dto.AnswerForm;
import login.spring.dto.QuestionForm;
import login.spring.repository.QuestionRepository;
import login.spring.service.AnswerService;
import login.spring.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        return "user/questionForm";
    }

    @PostMapping("/question/create")
    public String postCreateQuestion(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @Valid @ModelAttribute("questionForm") QuestionForm questionForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            log.info("postCreateQuestion() has error for no-data");
            return "user/questionForm";
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

    @GetMapping("/question/list") // 질문 리스트 보기
    public String getQuestionList(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value="kw", defaultValue="") String kw,  Model model) {
        Page<Question> paging = questionService.getList(page, kw);
        List<Question> questionAnswerCount = questionService.getQuestionReplySize();

        log.info("answerList? : ", questionAnswerCount);
        model.addAttribute("paging", paging);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("kw", kw);
        log.info("getQuestionList() process complete");
        log.info("Total Question Count : " + paging.getPageable());
        return "user/questionList";
    }

    @GetMapping("/question/detail/{qid}") // 질문 상세 보기
    public String getQuestionListDetail(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @PathVariable("qid") Long qid, Model model, AnswerForm answerForm) {
        Optional<Question> detailQuestion = questionService.getListById(qid);

        if(detailQuestion.isPresent()){
            Question viewQuestion = detailQuestion.get();
            Question questionForm = new Question();

            questionForm.setSubject(viewQuestion.getSubject());
            questionForm.setContent(viewQuestion.getContent());
            questionForm.setAuthor(viewQuestion.getAuthor());
            questionForm.setCreateDate(viewQuestion.getCreateDate());
            questionForm.setId(viewQuestion.getId());
            questionForm.setAnswerList(viewQuestion.getAnswerList());
            questionForm.setModifyDate(viewQuestion.getModifyDate());

            model.addAttribute("answerForm", answerForm);
            model.addAttribute("question", questionForm); // viewQuestion.get()
            model.addAttribute("loginMember", loginMember);
            log.info("getQuestionDetail() process complete");
            return "user/questionDetail";
        }

        model.addAttribute("loginMember", loginMember);
        log.info("getQuestionDetail() process complete , search detail is not found");
        return "user/questionList";
    }

    @GetMapping("/question/modify/{qid}") // 질문 수정 페이지 열기
    public String getModifyQuestion(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @PathVariable("qid") Long qid, QuestionForm questionForm, Model model) {
        Optional<Question> question = questionService.getListById(qid);

        if(question.isPresent()) {
            log.info("getModifyQuestion() question object exist, keep the next process");
            questionForm.setSubject(question.get().getSubject());
            questionForm.setContent(question.get().getContent());

            model.addAttribute("questionForm", questionForm);
            model.addAttribute("loginMember", loginMember);
            log.info("getModifyQuestion() process complete");
            return "user/questionForm";
        }
        model.addAttribute("loginMember", loginMember);
        return "user/questionList";
    }

    @PostMapping("/question/modify/{qid}") // 질문 수정 하기
    public String postModifyQuestion(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @PathVariable("qid") Long qid, @Valid @ModelAttribute("questionForm") QuestionForm questionForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            return "user/questionForm";
        }
        log.info("postModifyQuestion() question object called from view, keep the next process");
        Optional<Question> originData = questionService.getListById(qid);
        Question question = new Question();
        question.setId(qid);
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setModifyDate(LocalDateTime.now());
        question.setCreateDate(originData.get().getCreateDate());
        question.setAuthor(originData.get().getAuthor());

        questionService.modifyQuestion(question);
        model.addAttribute("loginMember", loginMember);
        log.info("postModifyQuestion() process complete");
        return String.format("redirect:/question/detail/%s", qid);
    }

    @GetMapping("/question/delete/{qid}") // 질문 삭제 하기
    public String getDeleteQuestion(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @PathVariable("qid") Long qid, Model model) {
        model.addAttribute("loginMember", loginMember);

        Optional<Question> findQuestion = questionService.getListById(qid);
        Question question = findQuestion.get();

        questionService.deleteQuestion(question);
        model.addAttribute("loginMember", loginMember);

        log.info("getDeleteQuestion() data deletion finish, Question Id : ", qid);
        return "redirect:/question/list";
    }
}

package login.spring.service;

import login.spring.domain.Question;
import login.spring.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 폼을 받아서 저장 하기
    public void createQuestion(Question question) {
        this.questionRepository.save(question);
        log.info("createQuestion() process complete");
        log.info("### rawData ###\n{subject : " + question.getSubject() + ", createDate : " + question.getCreateDate() + "}\n### rawData ###");
        // return question.getId();
    }

    // 질문 폼을 수정 하기
    public void modifyQuestion(Question question) {
        this.questionRepository.save(question);
        log.info("modifyQuestion() process complete");
        log.info("### rawData ###\n{subject : " + question.getSubject() + ", modifyDate : " + question.getModifyDate() + "}\n### rawData ###");
    }

    // 전체 질문 조회
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(sorts));
        log.info("getList() process complete");
        return questionRepository.findAll(pageable);
    }

    // Question Answer 객수 조회
    public List<Question> getQuestionReplySize() {
        return this.questionRepository.findAll();
    }

    // QuestionId 질문 조회
    public Optional<Question> getListById(Long id) {
        log.info("getListById() process complete, searchId : " + id);
        return questionRepository.findById(id);
    }

    // QuestionId 질문 삭제
    public void deleteQuestion(Question question) {
        this.questionRepository.deleteById(question.getId());
        log.info("deleteQuestion() process complete");
    }

}

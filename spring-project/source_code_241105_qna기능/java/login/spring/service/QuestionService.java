package login.spring.service;

import login.spring.domain.Question;
import login.spring.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    // 전체 질문 조회
    public List<Question> getList() {
        log.info("findByAllQuestion() process complete");
        return questionRepository.findAll();
    }

    // QuestionId 질문 조회
    public Optional<Question> getListById(Long id) {
        log.info("getListById() process complete, searchId : " + id);
        return questionRepository.findById(id);
    }
}

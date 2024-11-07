package login.spring.service;

import login.spring.domain.Answer;
import login.spring.domain.Question;
import login.spring.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void createAnswer(Answer answer) {
        this.answerRepository.save(answer);
        log.info("createAnswer() process complete");
        log.info("### rawData ###\n{author : " + answer.getAuthor() + ",\n content : " + answer.getContent() + "}");
    }

    public Optional<Answer> getAnswerListById(Long id) {
        log.info("getAnswerListById() process complete");

        return this.answerRepository.findById(id);
    }

    // 질문 폼을 수정 하기
    public void modifyAnswer(Answer answer) {
        this.answerRepository.save(answer);
        log.info("modifyAnswer() process complete");
        log.info("### rawData ###\n{subject : " + answer.getContent() + ", modifyDate : " + answer.getModifyDate() + "}\n### rawData ###");
    }
}

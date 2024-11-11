package login.spring.service;

import jakarta.persistence.criteria.*;
import login.spring.domain.Answer;
import login.spring.domain.EzenMember;
import login.spring.domain.Question;
import login.spring.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        log.info("getList() pagable process");
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(sorts));
        log.info("getList() serch process");
        Specification<Question> spec = search(kw);
        log.info("getList() process complete");
        return questionRepository.findAll(spec, pageable);
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

    // 검색 기능 추가
    // JPA가 제공 하는 Specification 인터페이스 : DB 검색을 유연하게 할 수 있고, 복잡한 검색 조건도 처리 할 수 있음
    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);  // 중복을 제거
                Join<Question, EzenMember> m1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, EzenMember> m2 = a.join("author", JoinType.LEFT);
                return criteriaBuilder.or(
                        criteriaBuilder.like(q.get("subject"),
                                "%" + kw + "%"), // 제목
                        criteriaBuilder.like(q.get("content"),
                                "%" + kw + "%"), // 질문내용
                        criteriaBuilder.like(m1.get("name"),
                                "%" + kw + "%"), // 질문작성자
                        criteriaBuilder.like(a.get("content"),
                                "%" + kw + "%"), // 답변내용
                        criteriaBuilder.like(m2.get("name"),
                                "%" + kw + "%") // 답변작성자
                );
            }
        };
    }









}
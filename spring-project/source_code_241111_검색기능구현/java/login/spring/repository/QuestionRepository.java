package login.spring.repository;

import login.spring.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 제목으로 찾기
    Question findBySubject(String subject);

    // 제목과 내용으로 찾기
    Question findBySubjectAndContent(String subject, String content);

    // 질문엔티티의 subject 열 값 들 중에서 특정 문자열을 포함 하는 데이터 조회
    List<Question> findBySubjectLike(String subject);

    // findAll 메서드 (Page 자료형으로 사용함)
    Page<Question> findAll(Pageable pageable);

    // Specifiacation과 Pagable 객체를 사용하여 DB 에서 Question엔티티를 조회한 결과를 페이징 하여 반환
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}

/*
    페이징을 구현하기 위해 추가로 설치해야 하는 라이브러리는 없다.
    JPA 환경 구축 시 설치했던 JPA 관련 라이브러리에 이미 페이징을 위한 패키지들이 들어 있기 때문이다.
    그러므로 다음 클래스들을 이용하면 페이징을 쉽게 구현할 수 있다.
    org.springframework.data.domain.Page: 페이징을 위한 클래스이다.
    org.springframework.data.domain.PageRequest: 현재 페이지와 한 페이지에 보여 줄 게시물 개수 등을 설정하여 페이징 요청을 하는 클래스이다.
    org.springframework.data.domain.Pageable: 페이징을 처리하는 인터페이스이다.
 */
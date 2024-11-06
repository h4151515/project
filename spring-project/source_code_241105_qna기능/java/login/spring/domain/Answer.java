package login.spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Long id; // private Long answerId; ??

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne // Owner 상태임
    @JoinColumn(name = "member_id")
    private EzenMember author; // 글쓴이, String을 하지 않고, 매핑이 되기 위해 EzenMember 자료형으로 변경

    private LocalDateTime modifyDate;

    /*
        아래 메서드는 임의로 추가 함
        로그에 각 데이터 값을 보기 위해서
     */
    @Override
    public String toString() {
        return "{answer_id : " + id + ", author : " + author + ", createDate : " + createDate + "}";
    }
}

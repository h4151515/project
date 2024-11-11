package login.spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    @Column(length = 200) // 글자수 제한
    private String subject;

    @Column(columnDefinition = "TEXT") // 텍스트를 열 데이터로 넣을 수 있음을 의미 하고 글자수를 제한 할수 없는 경우에 사용함
    private String content;

    private LocalDateTime createDate;

    @ManyToOne // Owner 상태임
    @JoinColumn(name = "member_id")
    private EzenMember author; // 로그인 사용자, String을 하지 않고, 매핑이 되기 위해 EzenMember 자료형으로 변경

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    /*
       아래 메서드는 임의로 추가 함
       로그에 각 데이터 값을 보기 위해서
    */
    @Override
    public String toString() {
        return "{question_id : " + id + ", subject : " + subject + ", author : " + author + "}";
    }
}

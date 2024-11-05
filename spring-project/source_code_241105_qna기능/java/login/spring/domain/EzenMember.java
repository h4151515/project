package login.spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class EzenMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String loginId;

    private String name;

    private String password;

    private String grade;

    /*
        @OneToMany 사용법
        - Owner를 찾기 위함, mappedBy 의 @ManyToOne으로 적용 되어 있는 엔티티의 해당 속성을 선택
        - Question 이나 Answer의 내용이 바뀌는 경우 List 안의 내용도 업데이트가 되어야 하기 때문에
          종속적으로 자동 업데이트 하기 위해서 casecade를 이용 하여 CasecadeType.ALL을 적용
        - Question 엔티티에서 CascadeType.REMOVE 는 질문 삭제의 경우 자동 업데이트 하기 위해서 해당 @OneToMany 부분에 적용
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Question> questionList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Answer> answerList;

    /*
        아래 메서드는 임의로 추가 함
        로그에 각 데이터 값을 보기 위해서
     */
    @Override
    public String toString() {
        return "\n{userId : " + loginId + ",\nname : " + name + ",\npassword : " + password + "}\n";
    }
}

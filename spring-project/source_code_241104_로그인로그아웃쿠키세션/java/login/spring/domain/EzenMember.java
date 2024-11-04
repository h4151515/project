package login.spring.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class EzenMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grade;

    private String loginId;

    private String name;

    private String password;

    /*
        아래 메서드는 임의로 추가 함
        로그에 각 데이터 값을 보기 위해서
     */
    @Override
    public String toString() {
        return "{userId : " + loginId + ", name : " + name + ", password : " + password + "}";
    }
}

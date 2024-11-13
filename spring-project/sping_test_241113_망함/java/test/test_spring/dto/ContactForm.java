package test.test_spring.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactForm {

    private Long id;

    @NotEmpty(message = "이름은 필수 항목 입니다")
    private String name;
    @NotEmpty(message = "전화번호는 필수 항목 입니다")
    private String tel;
    @NotEmpty(message = "이메일은 필수 항목 입니다")
    private String email;
    @NotEmpty(message = "문의사항은 필수 항목 입니다")
    private String inquire;

    private boolean newsletter;

}

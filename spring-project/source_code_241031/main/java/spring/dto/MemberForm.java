package hello_v3.hello.v3.spring.dto;

/*
    DTO에서는 엔터티(@Entity)를 가지지 않음 (DAO만 가짐)
 */

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    private String userId;
    private String name;

}

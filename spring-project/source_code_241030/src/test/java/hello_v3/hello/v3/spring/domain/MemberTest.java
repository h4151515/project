package hello_v3.hello.v3.spring.domain;

import hello_v3.hello.v3.spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberTest {
    @Autowired private MemberRepository memberRepository;

    @Test
    public void 멤버생성() {
        Member member1 = new Member();
        member1.setUserId("goodluck123");
        member1.setName("굿럭123");
        this.memberRepository.save(member1);

        Member member2 = new Member();
        member2.setUserId("WTF123");
        member2.setName("와떠푹123");
        this.memberRepository.save(member2);
    }

    @Test
    public void 멤버조회() {
        Optional<Member> findMember = this.memberRepository.findByUserId("hello123");
        if(findMember.isPresent()) {
            Member member = findMember.get();
            assertEquals("hello123", member.getUserId());
        }
    }
}
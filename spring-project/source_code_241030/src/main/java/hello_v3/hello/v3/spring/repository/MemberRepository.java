package hello_v3.hello.v3.spring.repository;

import hello_v3.hello.v3.spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
     Optional<Member> findByUserId(String userId);
}

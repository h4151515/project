package login.spring.repository;

import login.spring.domain.EzenMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<EzenMember, Long> {

    Optional<EzenMember> findByLoginId(String loginId);
}

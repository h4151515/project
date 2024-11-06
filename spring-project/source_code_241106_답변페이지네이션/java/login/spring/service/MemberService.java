package login.spring.service;

import login.spring.domain.EzenMember;
import login.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(EzenMember member) {
        validateDuplicateMember(member);
        this.memberRepository.save(member);
        log.info("join() process complete (ID : " + member.getLoginId() + ", UserName : " + member.getName() + ")");
        return member.getId();
    }

    // 회원 정보 수정
    public Long save(EzenMember member) {
        this.memberRepository.save(member);
        log.info("update() process complete (ID : " + member.getLoginId() + ", UserName : " + member.getName() + ")");
        return member.getId();
    }

    // 중복검사
    public void validateDuplicateMember(EzenMember member) {
        Optional<EzenMember> findMember = memberRepository.findByLoginId(member.getLoginId());
        if (findMember.isPresent()) {
            throw new IllegalStateException("   validateDuplicateMember() has Duplicated UserId");
        }
    }

    // 전체 회원 조회
    public List<EzenMember> findByAllMember() {
        log.info("findByAllMember() process complete");
        return memberRepository.findAll();
    }

    // 회원 조회
    public Optional<EzenMember> findByMember(Long loginId) {
        log.info("findByMember() process complete");
        return memberRepository.findById(loginId);
    }

    // 회원 삭제
    public void deleteMember(EzenMember member) {
        log.info("deleteMember() process complete");
        this.memberRepository.deleteById(member.getId());
    }
}
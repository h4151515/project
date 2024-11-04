package login.spring.service;

import login.spring.domain.EzenMember;
import login.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(EzenMember member) {
        validateDuplicateMember(member);
        this.memberRepository.save(member);
        System.out.println(getClass() + "   join() process complete (ID : " + member.getLoginId() + ", UserName : " + member.getName() + ")");
        return member.getId();
    }

    // 회원 정보 수정
    public Long save(EzenMember member) {
        this.memberRepository.save(member);
        System.out.println(getClass() + "   update() process complete (ID : " + member.getLoginId() + ", UserName : " + member.getName() + ")");
        return member.getId();
    }

    // 중복검사
    public void validateDuplicateMember(EzenMember member) {
        Optional<EzenMember> findMember = memberRepository.findByLoginId(member.getLoginId());
        if (findMember.isPresent()) {
            throw new IllegalStateException(getClass() + "   validateDuplicateMember() has Duplicated UserId");
        }
    }

    // 전체 회원 조회
    public List<EzenMember> findByAllMember() {
        System.out.println(getClass() + "   findByAllMember() process complete");
        return memberRepository.findAll();
    }

    // 회원 조회
    public Optional<EzenMember> findByMember(Long loginId) {
        System.out.println(getClass() + "   findByMember() process complete");
        return memberRepository.findById(loginId);
    }

    // 회원 삭제
    public void deleteMember(EzenMember member) {
        System.out.println(getClass() + "   deleteMember() process complete");
        this.memberRepository.deleteById(member.getId());
    }
}
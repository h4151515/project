package hello_v3.hello.v3.spring.service;

import hello_v3.hello.v3.spring.domain.Member;
import hello_v3.hello.v3.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    이곳에서는 controller에서 웹에서 CRUD로 받게된 요청들을 처리 하기 위한 서비스 로직
    리포지토리에 접근 하여 데이터를 생성, 삽입, 수정, 삭제 등의 데이터 가공을 수행
    도메인 간의 객체 관계 정리
    입력데이터의 유효성 검사 및 비지니스 로직에 맞지 않는 경우 예외 발생
    (리소스 영역에서 웹 정보 요청 --> controller 영역 요청 확인 --> serivce 영역 비지니스 로직 수행 [CRUD])

         -->  DTO --        ---> 도메인(DAO)--
         |          |       |               |
    뷰(타입리프) --> 컨트롤러 --> 서비스 --> 레포지토리(h2)
     get,post                  CRUD

 */
@Service
@RequiredArgsConstructor // 생성자 자동 생성 (초기화 되지않은 final 필드, @NotNull이 붙은 필드)
public class MemberService {

    private final MemberRepository memberRepository; // final 안하고, 멤버 레포까지 넘어감 안하면 null로 리턴함

    // 회원가입
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        // member 객체의 정보를 저장
        this.memberRepository.save(member);
        System.out.println(getClass() + "   join() Signin Done (ID : " + member.getUserId() + ", UserName : " + member.getName() + ")");
        return member.getId(); // 저장 후 회원 아이디를 다시 리턴 (확인 차원)
    }

    // 중복 회원 검증을 위한 메서드
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember =  memberRepository.findByUserId(member.getUserId());
        if(findMember.isPresent()) {
            throw new IllegalStateException(getClass() + "   validateDuplicateMember()   이미 존재 하는 회원입니다");
        }
        System.out.println(getClass() + "   validateDuplicateMember() ID Check Done");
    }

    // 전체회원 조회
    public List<Member> findByAllMember() {
        System.out.println(getClass() + "   findByAllMember() ");

        return memberRepository.findAll();
    }

    // 회원 정보 조회
    public Optional<Member> findByMemeber(Long memberId) {
        System.out.println(getClass() + "   findByMemeber()");
        return memberRepository.findById(memberId);
    }

    // 회원 정보 삭제
    public void deleteByMember(Member member) {
        System.out.println(getClass() + "   deleteByMember()");
        this.memberRepository.deleteById(member.getId());
    }

}

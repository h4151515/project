package login.spring.service;

import login.spring.domain.EzenMember;
import login.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public EzenMember login(String loginId, String password) {
        Optional<EzenMember> findUserId = memberRepository.findByLoginId(loginId);
        log.info("login() insert UserInfo {userId : " + loginId + ", password : " + password + "}");
        log.info("inner rowData " + findUserId.toString());
        if(findUserId.isPresent()) {
            EzenMember findEzenMember = findUserId.get();
            if (findEzenMember.getPassword().equals(password)) {
                log.info("login() UserId, Password was correct");
                return findEzenMember;
            } else {
                log.info("login() password is not mismatch");
            }
            return null;
        }
        log.info("login() UserId is not mismatch");
        return null;
    }
}

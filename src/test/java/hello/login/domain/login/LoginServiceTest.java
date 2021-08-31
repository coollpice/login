package hello.login.domain.login;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    @Test
    void 테스트() {
        Member member = new Member();

        System.out.println(member.getId() == null);
    }

}
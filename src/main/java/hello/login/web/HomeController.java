package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId" , required = false) Long memberId , Model model) {

        // 로그인 X (쿠키가없음)
        if (memberId == null) {
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        // 회원정보가 없을 때 
        if (loginMember == null) {
            return "home";
        }
        
        // 성공로직
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
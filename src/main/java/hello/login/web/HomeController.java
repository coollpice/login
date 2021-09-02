package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    //    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

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

    //    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {


        Member session = (Member) sessionManager.getSession(request);
        // 로그인 X (쿠키가없음)
        if (session == null) {
            return "home";
        }

        // 성공로직
        model.addAttribute("member", session);
        return "loginHome";
    }

    //    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 불필요한 세션 생성 방지
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV4(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    ///////////////////////////////
    @GetMapping("/")
    public String homeLoginV5(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }



}
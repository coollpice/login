package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private final String[] WHITELIST = {"/login", "/", "/members/add", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("인증 필터 시작 = {}" , requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) { //로그인 상태가 아니라면
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인페이지로 redirect
                    httpResponse.sendRedirect("/login?redirectURL="+ requestURI); // 경로뒤 매개변수로 요청 URI 를 넘기면 로그인후 다시 그페이지 요청
                    return;
                }
            }
            chain.doFilter(request,response);

        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증체크 필터 종료. {}" ,requestURI);
        }

    }

    /**
     * 설정한 경로의 경우 인증체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(WHITELIST, requestURI); // 설정경로 배열과 요청 URI 비교
    }
}

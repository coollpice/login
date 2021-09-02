package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID ="logId";


    //컨트롤러 호출 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID , uuid);       //afterCompletion 에서 조회하기위해 request 에 저장

        //@RequestMapping : HandlerMethod
        //정적리소스 :  ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출한 컨트롤러의 모든 정보를 알수있다.
        }
        log.info("preHandle 호출");
        log.info("Request [{}][{}][{}]", uuid, requestURI, handler);
        return true;

    }

    //컨트롤러 호출 후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle 호출 [{}]", modelAndView);
    }

    //Http 응답 완료 후
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}][{}]" , uuid , requestURI , handler);

        if (ex != null) {
            log.error("afterCompletionError!" , ex);
        }
    }
}

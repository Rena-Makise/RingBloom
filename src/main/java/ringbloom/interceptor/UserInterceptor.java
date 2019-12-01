/* UserInterceptor.java
 * Description : 로그인 인터셉터 설정
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 컨트롤러 실행 전에 수행
	 * 로그인이 되어있지 않으면 로그인 페이지로 이동
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("======================== START ========================");
		log.debug("  Request URL \t: " + request.getRequestURI());
		try {
			if (request.getRequestURI().contains("/board")) {
				if (request.getSession().getAttribute("nickname") == null) {
					response.sendRedirect("/user/needLogin");
					return false;
				} else {
					return true;
				}
			} else if (request.getRequestURI().contains("/user/signup")) {
				if (request.getSession().getAttribute("nickname") != null) {
					response.sendRedirect("/user/alreadyLogin");
					return false;
				} else {
					return true;
				}
			} else if (request.getRequestURI().contains("/user/login")) {
				if (request.getSession().getAttribute("nickname") != null) {
					response.sendRedirect("/user/alreadyLogin");
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * 컨트롤러 수행 후 결과를 뷰에 보내기 전에 수행
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("======================== END ========================");
	}
	
}

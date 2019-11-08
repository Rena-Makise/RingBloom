/* LoggerInterceptor.java
 * Description : 각 요청의 시작과 끝을 보여주는 로그 출력 인터셉터
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 컨트롤러 실행 전에 수행
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("======================== START ========================");
		log.debug("  Request URL \t: " + request.getRequestURI());
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

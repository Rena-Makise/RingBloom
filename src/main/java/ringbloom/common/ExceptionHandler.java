/* ExceptionHandler.java
 * Description : 예외 처리
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
	/**
	 * 예외처리 메소드
	 * @param request
	 * @param exception
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		
		if (request.getSession().getAttribute("nickname") != null) {
			mv.addObject("nickname", request.getSession().getAttribute("nickname"));
			mv.addObject("login", "login");
			mv.addObject("email", request.getSession().getAttribute("email"));
		} else {
			mv.addObject("nickname", "Guest");
			mv.addObject("login", "need");
			mv.addObject("email", "");
		}
		
		log.error("exception", exception);
		
		return mv;
	}
}

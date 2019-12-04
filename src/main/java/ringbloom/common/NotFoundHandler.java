/* NotFoundHandler.java
 * Description : 에러 처리
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.common;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NotFoundHandler implements ErrorController {
	private static final String ERROR_PATH = "/error/error_notfound";
	
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
	
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        log.info("httpStatus : "+httpStatus.toString());
        model.addAttribute("code", status.toString());
        model.addAttribute("msg", httpStatus.getReasonPhrase());
        model.addAttribute("timestamp", new Date());
        
		if (request.getSession().getAttribute("nickname") != null) {
			model.addAttribute("nickname", request.getSession().getAttribute("nickname"));
			model.addAttribute("login", "login");
			model.addAttribute("email", request.getSession().getAttribute("email"));
		} else {
			model.addAttribute("nickname", "Guest");
			model.addAttribute("login", "need");
			model.addAttribute("email", "");
		}
        
        return "/error/error_notfound";
    }
	
}

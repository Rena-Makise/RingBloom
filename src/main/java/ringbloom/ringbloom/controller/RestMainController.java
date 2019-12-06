/* RestMainController.java
 * Description : REST 메인 컨트롤러
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.2 : 로그인 세션 처리 - 이 창 재
 */

package ringbloom.ringbloom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "RingBloom REST")
@Controller
public class RestMainController {
	
	@ApiOperation(value = "RingBloom 메인화면")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView openMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("index");
		if (request.getSession().getAttribute("nickname") != null) {
			mv.addObject("nickname", request.getSession().getAttribute("nickname"));
			mv.addObject("login", "login");
			mv.addObject("email", request.getSession().getAttribute("email"));
		} else {
			mv.addObject("nickname", "Guest");
			mv.addObject("login", "need");
			mv.addObject("email", "");
		}
		return mv;
	}
}

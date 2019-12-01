/* RestUserController.java
 * Description : REST 유저 컨트롤러
 * ver 0.1 : 초기 구성
 * ver 0.2 : 토큰 처리 추가
 */
package ringbloom.ringbloom.controller;

import java.io.PrintWriter;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ringbloom.common.SecurityUtil;
import ringbloom.ringbloom.dto.UserDto;
import ringbloom.ringbloom.service.UserService;

@Slf4j
@Api(description = "RingBloom 사용자 REST")
@Controller
public class RestUserController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "회원가입 화면")
	@RequestMapping(value = "/user/signup", method = RequestMethod.GET)
	public ModelAndView openSignUp(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView("/user/signUp");
		mv.addObject("nickname", "Guest");
		mv.addObject("email", "");
		return mv;
	}
	
	@ApiOperation(value = "회원가입 처리")
	@RequestMapping(value = "/user/signup", method = RequestMethod.POST)
	public ModelAndView execSignUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/user/login");
		UserDto userDto = new UserDto();
		SecurityUtil securityUtil = new SecurityUtil();
		Encoder encoder = Base64.getEncoder();
		userDto.setNickname(request.getParameter("NICKNAME"));
		userDto.setEmail(request.getParameter("EMAIL"));
		userDto.setPassword(securityUtil.encryptSHA256(request.getParameter("PASSWORD")));
		userDto.setOp1(encoder.encodeToString(request.getParameter("OP1").getBytes("UTF-8")));
		userDto.setAnswer1(encoder.encodeToString(request.getParameter("ANSWER1").getBytes("UTF-8")));
		userDto.setOp2(encoder.encodeToString(request.getParameter("OP2").getBytes("UTF-8")));
		userDto.setAnswer2(encoder.encodeToString(request.getParameter("ANSWER2").getBytes("UTF-8")));
		userService.signUp(userDto);
		
		mv.addObject("message", "회원가입이 완료되었습니다.");

		return mv;
	}
	
	@ApiOperation(value = "닉네임 중복 체크")
	@RequestMapping(value = "/user/nickname", method = RequestMethod.POST)
	@ResponseBody
	public int checkNickname(@RequestParam String nickname) throws Exception {
		int checkResult = userService.countNickname(nickname);
		return checkResult;
	}
	
	@ApiOperation(value = "로그인 화면")
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public ModelAndView openLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/user/login");
		mv.addObject("nickname", "Guest");
		mv.addObject("email", "");
		return mv;
	}
	
	@ApiOperation(value = "로그인 필요 처리")
	@RequestMapping(value = "/user/needLogin", method = RequestMethod.GET)
	public void needLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('로그인이 필요합니다'); window.location.href='/user/login';</script>");
		out.flush();
	}
	
	@ApiOperation(value = "로그인 불필요 처리")
	@RequestMapping(value = "/user/alreadyLogin", method = RequestMethod.GET)
	public void alreadyLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('이미 로그인된 상태입니다.'); window.location.href='/';</script>");
		out.flush();
	}
	
	@ApiOperation(value = "로그인 처리")
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ModelAndView execLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/");
		SecurityUtil securityUtil = new SecurityUtil();
		String nickname = request.getParameter("nickname");
		String password = securityUtil.encryptSHA256(request.getParameter("password"));
		UserDto user = userService.checkLogin(nickname, password);
		if (user != null) {
			request.getSession().setAttribute("nickname", user.getNickname());
			request.getSession().setAttribute("email", user.getEmail());
			request.getSession().setAttribute("token", user.getToken());
			request.getSession().setMaxInactiveInterval(60 * 30);
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인에 실패하였습니다'); window.location.href='/user/login';</script>");
			out.flush();
		}
		return mv;
	}
	
	@ApiOperation(value = "로그아웃 처리")
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public void execLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		userService.updateToken(request.getSession().getAttribute("nickname").toString(), "");
		request.getSession().removeAttribute("nickname");
		request.getSession().removeAttribute("email");
		request.getSession().removeAttribute("token");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('로그아웃되었습니다.'); window.location.href='/';</script>");
		out.flush();
	}
	
	@ApiOperation(value = "접근거부 화면")
	@RequestMapping(value = "/user/denied", method = RequestMethod.GET)
	public String openDenied() throws Exception {
		return "/user/denied";
	}
	
	@ApiOperation(value = "유저정보 화면")
	@RequestMapping(value = "/user/account", method = RequestMethod.GET)
	public String openUserInfo() throws Exception {
		return "/user/userInfo";
	}
	
	@ApiOperation(value = "관리자 화면")
	@RequestMapping(value = "/user/admin", method = RequestMethod.GET)
	public String openAdmin() throws Exception {
		return "/user/admin";
	}
	
	@ApiOperation(value = "토큰정보 업데이트")
	@RequestMapping(value = "/user/token", method = RequestMethod.POST)
	@ResponseBody
	public int updateToken(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam String token) throws Exception {
		int result = 0;
		String nickname = request.getSession().getAttribute("nickname").toString();
		
		if(request.getSession().getAttribute("token") == null) {
			log.debug("토큰갱신필요");
			log.debug("디바이스 토큰 : " + token);
			String beforeTokenUser = userService.checkTokenUser(token);
			if(beforeTokenUser != null) {
				log.debug("기존 토큰 유저 삭제 : " + beforeTokenUser);
				userService.updateToken(beforeTokenUser, "");
			}
			userService.updateToken(nickname, token);
			request.getSession().setAttribute("token", token);
			result = 1;
		} else {
			String sessionToken = request.getSession().getAttribute("token").toString();
			if (!token.equals(sessionToken)) {
				log.debug("토큰갱신필요");
				log.debug("디바이스 토큰 : " + token);
				log.debug("세션토큰 : " + sessionToken);
				userService.updateToken(nickname, token);
				request.getSession().setAttribute("token", token);
				result = 1;
			}
		}

		return result;
	}
}

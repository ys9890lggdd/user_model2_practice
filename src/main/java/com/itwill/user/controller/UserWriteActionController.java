package com.itwill.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwill.summer.mvc.Controller;
import com.itwill.user.User;
import com.itwill.user.UserService;

public class UserWriteActionController implements Controller{
	private UserService userService;	
	public UserWriteActionController() throws Exception{
		userService = new UserService();
	}
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		/*
		0  . GET방식요청일때 redirect:user_main.do  forwardPath 반환
		1  . 요청객체 인코딩설정
	    2  . 파라메타 받기
	    3  . UserService객체생성
	    4  . UserService.create() 메쏘드실행
	    5-1. 아이디중복이면  forward:WEB-INF/views/user_write_form.jsp forwardPath 반환  
	    5-2. 가입성공이면    redirect:user_login_form.do forwardPath 반환
	    */
		String forwardPath = "";
		try {	
			if(request.getMethod().equalsIgnoreCase("GET")) {
				forwardPath="redirect:user_main.do";
			}else {
				request.setCharacterEncoding("UTF-8");
				String userId = request.getParameter("userId");
				String password = request.getParameter("password");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				
				User newUser = new User(userId, password, name, email);
				int createResult = userService.create(newUser);
				if(createResult == -1) {
					/*
					 * 아이디 중복이면~
					 */
					String msg = userId + "는 이미 사용 중인 아이디입니다.";
					request.setAttribute("msg", msg);
					request.setAttribute("fuser", newUser);
					forwardPath = "forward:WEB-INF/views/user_write_form.jsp";
				}else {
					/*
					 * 가입성공이면~
					 */
					request.setAttribute("user", newUser);
					forwardPath="redirect:user_login_form.do";
					
				}
			}
				
			}catch (Exception e) {
				e.printStackTrace();
				forwardPath="forward:/WEB-INF/views/user_error.jsp";
			}	
			
			return forwardPath;
			}
}
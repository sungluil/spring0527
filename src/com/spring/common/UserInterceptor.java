package com.spring.common;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.spring.board.service.boardService;
import com.spring.board.vo.UserInfoVo;

public class UserInterceptor extends HandlerInterceptorAdapter {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	boardService boardService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			
			HttpSession session = request.getSession();
			if(session.getAttribute("userinfo") == null) {
				Cookie loginCookie = WebUtils.getCookie(request, "userCookie");
				
				if(loginCookie != null) {
					String SESSIONID = loginCookie.getValue();
					System.out.println("SESSIONID = "+SESSIONID);
					UserInfoVo userInfoVo = boardService.checkUserInfoCookie(SESSIONID);
					System.out.println("userInfoVo = "+userInfoVo);
					if(userInfoVo != null) {
						session.setAttribute("userinfo", userInfoVo);
						return true;
					}
				} else {
					response.sendRedirect("/board/memberLogin.do");
					return false;
				}
			} else {
				System.out.println("그냥 true 발생");
				return true;
			}
//			if(request.getSession().getAttribute("userinfo") == null) {
//				System.out.println("세션값을 찾지못함");
//				//세션정보없으면 쿠키정보 확인
//				Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
//				if(loginCookie != null) {
//					//쿠키에서 세션을 꺼냄
//					HashMap<String, Object> sessionMap = new HashMap<String, Object>();
//					sessionMap.put("SESSIONID", loginCookie.getValue());
//					
//					//세션 ID값을 기준으로 검색하여 로그인 정보를 가져옴
//					HashMap<Object, Object> resultMap = boardService.checkUserInfoCookie(sessionMap);
//					if(resultMap != null) {
//						request.getSession().setAttribute("userinfo", resultMap);
//						request.getSession().setMaxInactiveInterval(60);
//						return true;
//					} 
//				} else {
//					response.sendRedirect(request.getContextPath() + "/board/memberlogin.do");
//					return false;
//				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.preHandle(request, response, handler);
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
}

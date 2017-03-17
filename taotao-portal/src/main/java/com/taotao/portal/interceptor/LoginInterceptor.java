package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.Impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;
	
	//拦截url，在Handler执行之前 执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object Handler) throws Exception {
    //preHandle返回值为true则执行handler
		//判断用户是否登录
		//从cookie中取token 
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		
		//根据token换取用户信息,调用sso系统的接口
		TbUser user = userService.getUserByToken(token);
		
		//取不到用户
		if (null==user) {
			//则需要跳转到登录页面，把用户请求的url传递给登录页面
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN +
					 "?redirect=" +"http://localhost:8082"+ request.getRequestURI());
			//返回false
			return false;	
		}
		//取到用户信息，则放行
		return true;
	}
	
	//在Handler执行之后，返回ModelAndView之前执行
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

}

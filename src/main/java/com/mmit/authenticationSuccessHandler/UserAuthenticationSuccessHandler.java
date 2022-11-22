package com.mmit.authenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mmit.model.entity.User;
import com.mmit.model.service.UserService;


@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("Authentication success : " + authentication.getName());
		
		request.setAttribute("userName", authentication.getName());

		System.out.println(request.getContextPath() + "/user/extend-expired_date");
		request.getRequestDispatcher(request.getContextPath() + "/user/extend-expired_date").forward(request, response);
		//System.out.println();
		//response.sendRedirect("/");
	}

}

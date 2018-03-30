package com.kosmo.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AuthFailureHandler implements AuthenticationFailureHandler {

	private String failureURL;	/*/member/loginPage?error*/
	public void setFailureURL(String failureURL) {
		this.failureURL = failureURL;

	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		System.out.println("AuthFailureHandler 실행...." + failureURL);
		System.out.println(e.getExtraInformation());
		System.out.println(e.getMessage());
		System.out.println(e.getAuthentication());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.sendRedirect(failureURL);
	}


}

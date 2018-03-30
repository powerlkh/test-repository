package com.kosmo.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	private String successURL;	/* /member/list */
	public void setSuccessURL(String successURL) {
		this.successURL = successURL;

	}

//	private MemberSerive servie;

	@Override
	public void onAuthenticationSuccess
	(HttpServletRequest request,
			HttpServletResponse response,
			Authentication auth)
			throws IOException, ServletException {

		System.out.println("AuthSuccessHandler 실행...." + successURL);

		HttpSession session = request.getSession();
		session.setAttribute("sess_username", auth.getName());


		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(successURL);

	}

}

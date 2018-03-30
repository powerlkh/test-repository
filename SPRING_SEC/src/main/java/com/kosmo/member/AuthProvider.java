package com.kosmo.member;


import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.kosmo.member.AuthServiceImpl;
import com.kosmo.member.AuthVO;

//@Component
public class AuthProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(AuthProvider.class);

//	@Autowired
//	MemberService memberService;


	@Autowired
	//UserDetailService userDetailService;
	AuthServiceImpl authServiceImpl;  //AuthVO MemberVO--> MemberService-Mapper


//	@Autowired
//	private PasswordEncoder passwordEncoder;



	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();  //vo.getMid()
		String password = (String) authentication.getCredentials();  //vo.getMpw()

		AuthVO authVO;
		Collection<? extends GrantedAuthority> authorities;

		try {
			authVO = authServiceImpl.loadUserByUsername(username);
//			authVO.getMemberVO().getMpw();
//			authVO.getPassword();

			if(!password.equals(authVO.getPassword())) {
				System.out.println(authVO.getPassword() +"," + password);
				System.out.println("비밀번호가 일치하지 않습니다. exception 발생");
				throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
			}

			System.out.println(authVO.getPassword() +"," + password);
//			String hashedPassword = passwordEncoder.encodePassword(password, "KOSMO-SEED");

			authorities = authVO.getAuthorities();  //ROLL_ADMIN ROLL_USER

		} catch(UsernameNotFoundException e) {
				logger.info(e.toString());
				throw new UsernameNotFoundException(e.getMessage()); } catch(BadCredentialsException e) {
				logger.info(e.toString());
				throw new BadCredentialsException(e.getMessage());
		} catch(Exception e) {
			logger.info(e.toString());
			throw new RuntimeException(e.getMessage()); }
			return new UsernamePasswordAuthenticationToken(authVO, password, authorities);
		}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}



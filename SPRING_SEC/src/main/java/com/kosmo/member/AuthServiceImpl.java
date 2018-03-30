package com.kosmo.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kosmo.member.MemberService;
import com.kosmo.member.MemberVO;

@Service
public class AuthServiceImpl implements UserDetailsService {

	@Autowired
	MemberService memberService;
//	public void setMemberServiceImpl(MemberService memberService) {
//		this.memberService = memberService;
//	}


	//static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	static final ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();

	@Override
	public AuthVO loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("AuthServiceImpl 실행....");
		if(memberService == null) System.out.println("memberService null....");
		//-------------------------------------------
		//DB에서 회원 정보  데이터를 읽어 옴.
		MemberVO memberVO = memberService.memberSearchByID(username);
		if (memberVO == null) {
			throw new UsernameNotFoundException("Not found with username");
		}

//		Map<String, Object> user = sqlSession.selectOne("user.selectUser",username);
//		if(user == null ) throw new UsernameNotFoundException(username);

		//-------------------------------------------


		//Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		//
		//if(memberVO.getMpw() !=authVO.getPassword()

		if (memberVO.getMgubun() != null && memberVO.getMgubun().trim().length() > 0) {
            if (memberVO.getMgubun().equals("a")) {
            	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
            	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
        }
		//vo.getMpw() ==
		//String encodedPassword = passwordEncoder.encode(memberVO.getMpw());
		//String mpwSha = shaPasswordEncoder.encodePassword(memberVO.getMpw(), "KOSMO-SEED");

		AuthVO authVO = new AuthVO(username, memberVO.getMpw(),
				true, true, true, true
				, authorities, memberVO);

		return authVO;
	}

}

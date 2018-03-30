package com.kosmo.member;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;


public class AuthVO extends User { //implements UserDetails {


	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private MemberVO memberVO;




	public AuthVO(
			String username, String password,
			boolean enabled,
			boolean accountNonExpired,
			boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			MemberVO memberVO) {
		super(username, password, enabled, accountNonExpired
				, credentialsNonExpired, accountNonLocked
				, authorities);
		this.memberVO = memberVO;
	}


	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	@Override
//	public String toString() {
//		return super.toString() + "; MemberVO: "+ this.memberVO;
//	}
}
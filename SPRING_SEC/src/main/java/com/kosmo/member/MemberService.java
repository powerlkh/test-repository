package com.kosmo.member;

import java.util.ArrayList;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


public interface MemberService {

//	@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
	public MemberVO memberSearchByID(String mid);

//	@Secured("ROLE_ADMIN")
	public int memberDelete(int mid);

//	@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
	public int memberInsert(MemberVO memberVO);

//	<global-method-security jsr250-annotations="enabled" />
//	@Secured("ROLE_ADMIN, ROLE_USER")


//	<global-method-security pre-post-annotations="enabled" />
//	<global-method-security>
//		<protect-pointcut expression="execution(* com.mycompany.*Service.*(..))" access="ROLE_USER"/>
//	</global-method-security>
//	@PreAuthorize("isAnonymous()")
	public ArrayList<MemberVO> memberList();


}

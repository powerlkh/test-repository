package com.kosmo.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.kosmo.member.MemberVO;

@Repository("memberMapper")
public interface MemberMapper {
	public MemberVO memberSearchByID(String mid);
	public int memberDelete(int mid);
	public int memberInsert(MemberVO memberVO);
	public ArrayList<MemberVO> memberList();
}

package com.kosmo.member;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosmo.mapper.MemberMapper;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public MemberVO memberSearchByID(String mid) {
		return memberMapper.memberSearchByID(mid);
	}

	@Override
	public int memberDelete(int mid) {
		return memberMapper.memberDelete(mid);
	}

	@Override
	public int memberInsert(MemberVO memberVO) {
		return memberMapper.memberInsert(memberVO);
	}

	@Override
	public ArrayList<MemberVO> memberList() {
		return memberMapper.memberList();
	}

}

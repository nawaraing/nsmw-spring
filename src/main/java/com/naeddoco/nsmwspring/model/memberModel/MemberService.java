package com.naeddoco.nsmwspring.model.memberModel;

import java.util.List;

public interface MemberService {
	
	public List<MemberDTO> selectAll(MemberDTO memberDTO);
	public MemberDTO selectOne(MemberDTO memberDTO);
	public boolean insert(MemberDTO memberDTO);
	public boolean update(MemberDTO memberDTO);
}
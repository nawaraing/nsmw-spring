package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import java.util.List;

public interface KakaoMemberService {
	public List<KakaoMemberDTO> selectAll(KakaoMemberDTO kakaoMemberDTO);
	public KakaoMemberDTO selectOne(KakaoMemberDTO kakaoMemberDTO);
	public boolean insert(KakaoMemberDTO kakaoMemberDTO);
	public boolean update(KakaoMemberDTO kakaoMemberDTO);
	public boolean delete(KakaoMemberDTO kakaoMemberDTO);
}

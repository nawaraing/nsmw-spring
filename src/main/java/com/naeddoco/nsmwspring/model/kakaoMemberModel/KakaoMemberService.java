package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import java.util.List;

public interface KakaoMemberService {
	public List<KakaoMemberDTO> selectAll(KakaoMemberDTO kakaoDTO);
	public KakaoMemberDTO selectOne(KakaoMemberDTO kakaoDTO);
	public boolean insert(KakaoMemberDTO kakaoDTO);
	public boolean update(KakaoMemberDTO kakaoDTO);
	public boolean delete(KakaoMemberDTO kakaoDTO);
}

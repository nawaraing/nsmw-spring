package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("kakaoMemberService")
public class KakaoMemberServiceImpl implements KakaoMemberService {

	@Autowired
	private KakaoMemberDAO kakaoMemberDAO;

	@Override
	public List<KakaoMemberDTO> selectAll(KakaoMemberDTO kakaoDTO) {
		return kakaoMemberDAO.selectAll(kakaoDTO);
	}

	@Override
	public KakaoMemberDTO selectOne(KakaoMemberDTO kakaoDTO) {
		return kakaoMemberDAO.selectOne(kakaoDTO);
	}

	@Override
	public boolean insert(KakaoMemberDTO kakaoDTO) {
		return kakaoMemberDAO.insert(kakaoDTO);
	}

	@Override
	public boolean update(KakaoMemberDTO kakaoDTO) {
		return kakaoMemberDAO.update(kakaoDTO);
	}

	@Override
	public boolean delete(KakaoMemberDTO kakaoDTO) {
		return kakaoMemberDAO.delete(kakaoDTO);
	}
	
}

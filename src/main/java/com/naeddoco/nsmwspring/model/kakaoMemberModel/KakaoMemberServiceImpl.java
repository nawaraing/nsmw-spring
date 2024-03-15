package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("kakaoMemberService")
public class KakaoMemberServiceImpl implements KakaoMemberService {

	@Autowired
	private KakaoMemberDAO kakaoMemberDAO;

	@Override
	public List<KakaoMemberDTO> selectAll(KakaoMemberDTO kakaoMemberDTO) {
		return kakaoMemberDAO.selectAll(kakaoMemberDTO);
	}

	@Override
	public KakaoMemberDTO selectOne(KakaoMemberDTO kakaoMemberDTO) {
		return kakaoMemberDAO.selectOne(kakaoMemberDTO);
	}

	@Override
	public boolean insert(KakaoMemberDTO kakaoMemberDTO) {
		return kakaoMemberDAO.insert(kakaoMemberDTO);
	}

	@Override
	public boolean update(KakaoMemberDTO kakaoMemberDTO) {
		return kakaoMemberDAO.update(kakaoMemberDTO);
	}

	@Override
	public boolean delete(KakaoMemberDTO kakaoMemberDTO) {
		return kakaoMemberDAO.delete(kakaoMemberDTO);
	}
	
}

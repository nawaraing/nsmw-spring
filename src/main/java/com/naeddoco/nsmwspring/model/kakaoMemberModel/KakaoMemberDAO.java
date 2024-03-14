package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;


@Repository("kakaoMemberDAO")
public class KakaoMemberDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECTALL = "SELECT KAKAO_ID, MEMBER_ID FROM KAKAO_MEMBER";
	
	// 로그인(KAKAO 회원)
	// KAKAO_ID와 일치하는 MEMBER_ID 반환
	private static final String SELECTONE_KAKAO_MEMBER_LOGIN = "SELECT MEMBER_ID FROM KAKAO_MEMBER WHERE KAKAO_ID = ?";
	private static final String INSERT = "";
	private static final String UPDATE = "";
	private static final String DELETE = "";
	
	
	public List<KakaoMemberDTO> selectAll(KakaoMemberDTO kakaoMemberDTO) {

		return (List<KakaoMemberDTO>) jdbcTemplate.query(SELECTALL, new KakaoMemberRowMapper());

	}
	
	
	public KakaoMemberDTO selectOne(KakaoMemberDTO kakaoMemberDTO) {
		
		if(kakaoMemberDTO.getSearchCondition().equals("kakaoLogin")) {
			
			Object[] args = { kakaoMemberDTO.getKakaoID()};

			try {

				return jdbcTemplate.queryForObject(SELECTONE_KAKAO_MEMBER_LOGIN, args, new KakaoMemberRowMapper());

			} catch (Exception e) {

				return null;
			}
		}	
		return null;
	}

	
	public boolean insert(KakaoMemberDTO kakaoMemberDTO) {

//		int result = jdbcTemplate.update(INSERT,));
//
//		if (result <= 0) {
//
//			return false;
//		}
		return true;
	}

	
	public boolean update(KakaoMemberDTO kakaoMemberDTO) {

//		int result = jdbcTemplate.update(UPDATE,);
//
//		if (result <= 0) {
//
//			return false;
//		}
		return true;
	}

	
	public boolean delete(KakaoMemberDTO kakaoMemberDTO) {
//
//		int result = jdbcTemplate.update(DELETE,);
//
//		if (result <= 0) {
//
//			return false;
//		}
		return true;
	}

}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class KakaoMemberRowMapper implements RowMapper<KakaoMemberDTO> {

	@Override
	public KakaoMemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		KakaoMemberDTO kakaoMemberDTO = new KakaoMemberDTO();

		kakaoMemberDTO.setKakaoID(rs.getString("KAKAO_ID"));
		kakaoMemberDTO.setMemberID(rs.getString("MEMBER_ID"));
		
		log.info(rs.getString("KAKAO_ID"));
		log.info(rs.getString("MEMBER_ID"));

		return kakaoMemberDTO;

	}


}

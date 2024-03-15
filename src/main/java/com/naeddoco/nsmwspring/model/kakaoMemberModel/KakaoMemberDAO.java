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
	// MEMBER_STATE('JOIN')도 함께 확인 후 결과 반환
	private static final String SELECTONE_KAKAO_MEMBER_LOGIN = "SELECT M.MEMBER_ID "
			+ "FROM MEMBER AS M "
			+ "INNER JOIN KAKAO_MEMBER AS KM "
			+ "ON M.MEMBER_ID = KM.MEMBER_ID "
			+ "WHERE M.MEMBER_STATE = 'JOIN' "
			+ "AND KM.KAKAO_ID = ?";
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

//		int result = jdbcTemplate.update(INSERT,kakaoMemberDTO.getKakaoID(),kakaoMemberDTO.getMemberID());
//
//		if (result <= 0) {
//
			return false;
//		}
//		return true;
	}

	
	public boolean update(KakaoMemberDTO kakaoMemberDTO) {

//		int result = jdbcTemplate.update(UPDATE,kakaoMemberDTO.getKakaoID(),kakaoMemberDTO.getMemberID());
//
//		if (result <= 0) {
//
			return false;
//		}
//		return true;
	}

	
	public boolean delete(KakaoMemberDTO kakaoMemberDTO) {
//
//		int result = jdbcTemplate.update(DELETE,kakaoMemberDTO.getKakaoID(),kakaoMemberDTO.getMemberID());
//
//		if (result <= 0) {
//
			return false;
//		}
//		return true;
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

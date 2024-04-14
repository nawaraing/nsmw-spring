package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.imageModel.ImageDAO;

import lombok.extern.slf4j.Slf4j;


@Repository("kakaoMemberDAO")
@Slf4j
public class KakaoMemberDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	// KAKAO테이블의 모든 정보 조회-미사용
	private static final String SELECTALL = "SELECT KAKAO_ID, MEMBER_ID FROM KAKAO_MEMBER";
	 	
	// 로그인(KAKAO 회원)
	// KAKAO_ID와 일치하는 MEMBER_ID 반환
	// MEMBER_STATE('JOIN')도 함께 확인 후 결과 반환
	private static final String SELECTONE_KAKAO_MEMBER_LOGIN = "SELECT M.MEMBER_ID "
															 + "FROM MEMBER AS M "
															 + "INNER JOIN KAKAO_MEMBER AS KM "
															 + "ON M.MEMBER_ID = KM.MEMBER_ID "
															 + "WHERE M.MEMBER_STATE = 'JOIN' "
															 + "AND KM.KAKAO_ID = ?"; */

/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	

	/*
	public List<KakaoMemberDTO> selectAll(KakaoMemberDTO kakaoMemberDTO) {

		log.trace("selectAll 진입");
		
		try {
			return (List<KakaoMemberDTO>) jdbcTemplate.query(SELECTALL, new KakaoMemberRowMapper());
			
		}catch (Exception e) {
			
			log.error("selectAll 예외/실패");
			return null;

		}
	}
	
	public KakaoMemberDTO selectOne(KakaoMemberDTO kakaoMemberDTO) {
		
		log.trace("selectOne 진입");
		
		if(kakaoMemberDTO.getSearchCondition().equals("kakaoLogin")) {
			
			Object[] args = { kakaoMemberDTO.getKakaoID()};

			try {
				
				log.trace("kakaoLogin 진입");
				
				return jdbcTemplate.queryForObject(SELECTONE_KAKAO_MEMBER_LOGIN, args, new KakaoMemberLoginRowMapper());

			} catch (Exception e) {
				
				log.error("kakaoLogin 예외처리");
				return null;
			}
		}	
		
		log.error("selectOne 진입 실패");
		return null;
	}
*/
	
}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/

/*
@Slf4j
class KakaoMemberRowMapper implements RowMapper<KakaoMemberDTO> {

	@Override
	public KakaoMemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		KakaoMemberDTO kakaoMemberDTO = new KakaoMemberDTO();

		log.trace("KakaoMemberRowMapper 진입");
		
		kakaoMemberDTO.setKakaoID(rs.getString("KAKAO_ID"));
		kakaoMemberDTO.setMemberID(rs.getString("MEMBER_ID"));
		
		log.debug(rs.getString("KAKAO_ID"));
		log.debug(rs.getString("MEMBER_ID"));
		
		log.trace("KakaoMemberRowMapper 완료");
		
		return kakaoMemberDTO;

	}
}

@Slf4j
class KakaoMemberLoginRowMapper implements RowMapper<KakaoMemberDTO> {

	@Override
	public KakaoMemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		KakaoMemberDTO kakaoMemberDTO = new KakaoMemberDTO();

		log.trace("KakaoMemberLoginRowMapper 진입");
		
		kakaoMemberDTO.setMemberID(rs.getString("MEMBER_ID"));
		
		log.debug(rs.getString("MEMBER_ID"));

		log.trace("KakaoMemberLoginRowMapper 완료");
		
		return kakaoMemberDTO;

	}
}

*/
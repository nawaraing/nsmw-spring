package com.naeddoco.nsmwspring.model.memberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("memberDAO")
public class MemberDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private static final String SELECTALL = "SELECT MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, "
			+ "DAY_OF_BIRTH, GENDER, PHONE_NUMBER, EMAIL, AUTHORITY, MEMBER_STATE FROM MEMBER";
	
	// 로그인(내또코 회원) + 마이페이지PW확인
	// 아이디와 비밀번호가 같은 행의 아이디를 확인
	// MEMBER_STATE가 JOIN인지도 함께 확인
	// 로그인시 MEMBER_STATE가 JOIN이 아닐 경우 로그인 실패
	private static final String SELECTONE_MEMBER_LOGIN = "SELECT MEMBER_ID FROM MEMBER "
			+ "WHERE MEMBER_ID = ? "
			+ "AND MEMBER_PASSWORD = ? "
			+ "AND MEMBER_STATE = 'JOIN'";

	//회원 탈퇴시 해당하는 MEMBER_ID의 MEMBER_STATE를 'LEAVE'로 변경
	//해당 회원의 ID 삭제시 구매내역 등 여러 정보가 불일치될 수 있기때문에 회원 상태만 변경함
	private static final String UPDATE_MEMBER_STATE = "UPDATE MEMBER "
			+ "SET MEMBER_STATE = 'LEAVE' "
			+ "WHERE MEMBER_ID = ? ";

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

//	// 개인정보(정보 및 비밀번호)변경 진입 시 비밀번호 확인
//	// 아이디와 비밀번호가 같은 행의 회원아이디와 이름을 선택
//	private static final String SELECTONE_MEMBER_PW_CHECK = "SELECT M_ID, M_NAME FROM MEMBER WHERE M_ID=? AND M_PASSWORD = ?";
//
//	// 회원 주문정보(구매페이지 정보)
//	// 회원이 상품을 주문할 사용하며 이름, 전화번호 등을 선택한다
//	private static final String SELECTONE_MEMBER_BUYPAGE = "SELECT M_NAME, PHONE_NUMBER, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS,EMAIL FROM MEMBER WHERE M_ID=?";
//
//	// 마이페이지(이름, 생년월일, 성별, 전화번호, 이메일, 주소)
//	// 해당회원의 회원이름, 생년월일 등을 선택
//	private static final String SELECTONE_MEMBER_MYPAGE = "SELECT M_NAME, DOB, GENDER, PHONE_NUMBER, EMAIL, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS, HEALTH FROM MEMBER WHERE M_ID=?";
//
//	// 아이디 중복검사
//	// 해당 아이디가 일치하는 행이 있다면 해당 행의 M_ID를 선택
//	// 참고 : ID체크시 혹시 아이디가 1개 초과일수 있으니 ALL로 하는게 정확할 수 있음
//	private static final String SELECTONE_MEMBER_ID_CHECK = "SELECT M_ID FROM MEMBER WHERE M_ID=?";
//
//	// 회원 건강상태
//	// 해당 회원의 건강상태를 선택
//	private static final String SELECTONE_MEMBER_HEALTH = "SELECT HEALTH FROM MEMBER WHERE M_ID=?";
//
//	// 회원가입
//	// 회원가입에 필요한 데이터를 받아 DB에 추가한다
//	private static final String INSERT_MEMBER = "INSERT INTO "
//			+ "MEMBER (M_ID, M_NAME, M_PASSWORD, DOB, GENDER, PHONE_NUMBER, EMAIL, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS, GRADE, HEALTH, LOGIN_TYPE, KAKAO_ID) "
//			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'USER', ?, ?, ?)";
//
//	// 개인정보변경(이름, 생년월일, 성별, 전화번호, 이메일, 주소)
//	// 해당 회원의 개인정보를 변경한다
//	private static final String UPDATE_MEMBER_INFO = "UPDATE MEMBER " + "SET " + "M_NAME = ?, " + "PHONE_NUMBER = ?, "
//			+ "EMAIL = ?, " + "M_POSTCODE = ?, " + "M_ADDRESS = ?, " + "M_DETAILED_ADDRESS = ? " + "WHERE M_ID = ?";
//
//	// 비밀번호번경
//	// 해당 회원의 비밀번호를 변경한다
//	private static final String UPDATE_MEMBER_PW = "UPDATE MEMBER SET M_PASSWORD=? WHERE M_ID=?";
//	
//	private static final String DELETE = "";

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<MemberDTO> selectAll(MemberDTO memberDTO) {

		return (List<MemberDTO>) jdbcTemplate.query(SELECTALL, new MemberRowMapper());

	}

	public MemberDTO selectOne(MemberDTO memberDTO) {
		if (memberDTO.getSearchCondition().equals("memberLogin")) {
			
			Object[] args = { memberDTO.getMemberID(), memberDTO.getMemberPassword() };

			try {

				return jdbcTemplate.queryForObject(SELECTONE_MEMBER_LOGIN, args, new MemberRowMapper());

			} catch (Exception e) {

				return null;
			}
		}
		return null;
	}

	public boolean insert(MemberDTO memberDTO) {

//		int result = jdbcTemplate.update(INSERT_MEMBER, memberDTO.getMemberID(), memberDTO.getMemberPassword(),
//				memberDTO.getMemberName(), memberDTO.getDayOfBirth(), memberDTO.getGender(), memberDTO.getPhoneNumber(),
//				memberDTO.getEmail(), memberDTO.getAuthority(), memberDTO.getMemberState());
//
//		if (result <= 0) {
			return false;
//		}
//		return true;
	}

	
	public boolean update(MemberDTO memberDTO) {
		
		if (memberDTO.getSearchCondition().equals("memberleave")) {
			int result = jdbcTemplate.update(UPDATE_MEMBER_STATE, memberDTO.getMemberState());

			if (result <= 0) {
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean delete(MemberDTO memberDTO) {

//		int result = jdbcTemplate.update(DELETE, memberDTO.getMemberID(), memberDTO.getMemberPassword());
//
//		if (result <= 0) {
			return false;
//		}
//		return true;
	}
}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class MemberRowMapper implements RowMapper<MemberDTO> {

	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		MemberDTO memberDTO = new MemberDTO();

		memberDTO.setMemberID(rs.getString("MEMBER_ID"));
		memberDTO.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
		memberDTO.setMemberName(rs.getString("MEMBER_NAME"));
		memberDTO.setDayOfBirth(rs.getString("DAY_OF_BIRTH"));
		memberDTO.setGender(rs.getString("GENDER"));
		memberDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));
		memberDTO.setEmail(rs.getString("EMAIL"));
		memberDTO.setAuthority(rs.getString("AUTHORITY"));
		memberDTO.setMemberState(rs.getString("MEMBER_STATE"));

		log.info(rs.getString("MEMBER_ID"));
		log.info(rs.getString("MEMBER_PASSWORD"));
		log.info(rs.getString("MEMBER_NAME"));
		log.info(rs.getString("DAY_OF_BIRTH"));
		log.info(rs.getString("GENDER"));
		log.info(rs.getString("PHONE_NUMBER"));
		log.info(rs.getString("EMAIL"));
		log.info(rs.getString("AUTHORITY"));
		log.info(rs.getString("MEMBER_STATE"));

		return memberDTO;

	}

}
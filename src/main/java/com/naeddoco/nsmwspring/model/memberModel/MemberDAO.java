package com.naeddoco.nsmwspring.model.memberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("memberDAO")
public class MemberDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private static final String SELECTALL = "SELECT MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, DAY_OF_BIRTH, GENDER, PHONE_NUMBER, EMAIL, AUTHORITY, MEMBER_STATE FROM MEMBER";
	
	// 로그인(내또코 회원) + 마이페이지PW확인
	// 아이디와 비밀번호가 같은 행의 아이디를 선택
	private static final String SELECTONE_MEMBER_LOGIN = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID=? AND MEMBER_PASSWORD = ?";

	// 로그인(KAKAO 회원)
	// KAKAO_ID가 같은 행의 아이디를 선택
	private static final String SELECTONE_MEMBER_KAKAO_LOGIN = "SELECT M_ID FROM MEMBER WHERE KAKAO_ID = ?";

	// 개인정보(정보 및 비밀번호)변경 진입 시 비밀번호 확인
	// 아이디와 비밀번호가 같은 행의 회원아이디와 이름을 선택
	private static final String SELECTONE_MEMBER_PW_CHECK = "SELECT M_ID, M_NAME FROM MEMBER WHERE M_ID=? AND M_PASSWORD = ?";

	// 회원 주문정보(구매페이지 정보)
	// 회원이 상품을 주문할 사용하며 이름, 전화번호 등을 선택한다
	private static final String SELECTONE_MEMBER_BUYPAGE = "SELECT M_NAME, PHONE_NUMBER, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS,EMAIL FROM MEMBER WHERE M_ID=?";

	// 마이페이지(이름, 생년월일, 성별, 전화번호, 이메일, 주소)
	// 해당회원의 회원이름, 생년월일 등을 선택
	private static final String SELECTONE_MEMBER_MYPAGE = "SELECT M_NAME, DOB, GENDER, PHONE_NUMBER, EMAIL, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS, HEALTH FROM MEMBER WHERE M_ID=?";

	// 아이디 중복검사
	// 해당 아이디가 일치하는 행이 있다면 해당 행의 M_ID를 선택
	private static final String SELECTONE_MEMBER_ID_CHECK = "SELECT M_ID FROM MEMBER WHERE M_ID=?";

	// 회원 건강상태
	// 해당 회원의 건강상태를 선택
	private static final String SELECTONE_MEMBER_HEALTH = "SELECT HEALTH FROM MEMBER WHERE M_ID=?";

	// 회원가입
	// 회원가입에 필요한 데이터를 받아 DB에 추가한다
	private static final String INSERT_MEMBER = "INSERT INTO "
			+ "MEMBER (M_ID, M_NAME, M_PASSWORD, DOB, GENDER, PHONE_NUMBER, EMAIL, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS, GRADE, HEALTH, LOGIN_TYPE, KAKAO_ID) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'USER', ?, ?, ?)";

	// 개인정보변경(이름, 생년월일, 성별, 전화번호, 이메일, 주소)
	// 해당 회원의 개인정보를 변경한다
	private static final String UPDATE_MEMBER_INFO = "UPDATE MEMBER " + "SET " + "M_NAME = ?, " + "PHONE_NUMBER = ?, "
			+ "EMAIL = ?, " + "M_POSTCODE = ?, " + "M_ADDRESS = ?, " + "M_DETAILED_ADDRESS = ? " + "WHERE M_ID = ?";

	// 비밀번호번경
	// 해당 회원의 비밀번호를 변경한다
	private static final String UPDATE_MEMBER_PW = "UPDATE MEMBER SET M_PASSWORD=? WHERE M_ID=?";

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<MemberDTO> selectAll(MemberDTO memberDTO) {

		return (List<MemberDTO>) jdbcTemplate.query(SELECTALL, new MemberRowMapper());

	}

	public MemberDTO selectOne(MemberDTO memberDTO) {

		Object[] args = { memberDTO.getMemberID(), memberDTO.getMemberPassword() };

		try {

			return jdbcTemplate.queryForObject(SELECTONE, args, new MemberRowMapper());

		} catch (Exception e) {

			return null;

		}

	}

	public boolean insert(MemberDTO memberDTO) {

		int result = jdbcTemplate.update(INSERT, memberDTO.getMemberID(), memberDTO.getMemberPassword(),
				memberDTO.getMemberName(), memberDTO.getDayOfBirth(), memberDTO.getGender(), memberDTO.getPhoneNumber(),
				memberDTO.getEmail(), memberDTO.getAuthority(), memberDTO.getMemberState());

		if (result <= 0) {

			return false;

		}

		return true;

	}

	public boolean update(MemberDTO memberDTO) {

		int result = jdbcTemplate.update(UPDATE, memberDTO.getMemberID(), memberDTO.getMemberPassword(),
				memberDTO.getMemberName(), memberDTO.getDayOfBirth(), memberDTO.getGender(), memberDTO.getPhoneNumber(),
				memberDTO.getEmail(), memberDTO.getAuthority(), memberDTO.getMemberState());

		if (result <= 0) {

			return false;

		}

		return true;

	}

	public boolean delete(MemberDTO memberDTO) {

		int result = jdbcTemplate.update(DELETE, memberDTO.getMemberID(), memberDTO.getMemberPassword());

		if (result <= 0) {

			return false;

		}

		return true;

	}

}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
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

		System.out.println(rs.getString("MEMBER_ID"));
		System.out.println(rs.getString("MEMBER_PASSWORD"));
		System.out.println(rs.getString("MEMBER_NAME"));
		System.out.println(rs.getString("DAY_OF_BIRTH"));
		System.out.println(rs.getString("GENDER"));
		System.out.println(rs.getString("PHONE_NUMBER"));
		System.out.println(rs.getString("EMAIL"));
		System.out.println(rs.getString("AUTHORITY"));
		System.out.println(rs.getString("MEMBER_STATE"));

		return memberDTO;

	}

}
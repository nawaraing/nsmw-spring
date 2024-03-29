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
@Slf4j
public class MemberDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private static final String SELECTALL = "SELECT MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, DAY_OF_BIRTH, GENDER, PHONE_NUMBER, EMAIL, AUTHORITY, MEMBER_STATE FROM MEMBER";
	
	// 사용자 아이디로 사용자의 구독 정보를 가져오는 쿼리문
	private static final String SELECTALL_SUBSCRIPTION_BY_MEMBER = "SELECT" +
																   "SI.SUBSCRIPTION_INFO_ID, " +
																   "SI.BEGIN_DATE, " +
																   "SI.SUBSCRIPTION_TIMES, " +
																   "SI.NEXT_PAYMENT_DATE, " +
																   "SI.SUBSCRIPTION_POSTCODE, " +
																   "SI.SUBSCRIPTION_ADDRESS, " +
																   "SI.SUBSCRIPTION_DETAIL_ADDRESS, " +
																   "SI.SUBSCRIPTION_CLOSING_TIMES, " +
																   "SIP.PRODUCT_ID, " +
																   "SIP.QUANTITY, " +
																   "SIP.PURCHASE_PRICE " +
																   "FROM MEMBER M " +
																   "JOIN SUBSCRIPTION_INFO SI ON SI.MEMBER_ID = M.MEMBER_ID " +
																   "JOIN SUBSCRIPTION_INFO_PRODUCT SIP ON SIP.SUBSCRIPTION_INFO_ID = SI.SUBSCRIPTION_INFO_ID " +
																   "WHERE M.MEMBER_ID = ?";
	
	// 탈퇴여부가 JOIN 상태인 모든 사용자의 데이터를 가져오는 쿼리문
	private static final String SELECTALL_JOIN_MEMBER_INFO = "SELECT " +
														     "M.MEMBER_ID, " +
															 "CASE " +
															 "WHEN CHAR_LENGTH(M.MEMBER_NAME) <= 3 THEN REPLACE(M.MEMBER_NAME, SUBSTRING(M.MEMBER_NAME, 2, 1), '*') " +
															 "WHEN CHAR_LENGTH(M.MEMBER_NAME) > 3 THEN REPLACE(M.MEMBER_NAME, SUBSTRING(M.MEMBER_NAME, 2, CHAR_LENGTH(M.MEMBER_NAME) - 2), REPEAT('*', CHAR_LENGTH(M.MEMBER_NAME) - 2)) " +
															 "END AS MEMBER_NAME, " +
															 "M.DAY_OF_BIRTH, " +
															 "M.GENDER, " +
															 "M.PHONE_NUMBER, " +
															 "M.EMAIL, " +
															 "SA.SHIPPING_POSTCODE, " +
															 "SA.SHIPPING_ADDRESS, " +
															 "SA.SHIPPING_DETAIL_ADDRESS, " +
															 "G.GRADE_NAME, " +
															 "(SELECT GROUP_CONCAT(C.CATEGORY_NAME SEPARATOR ';') FROM CATEGORY C INNER JOIN MEMBER_CATEGORY MC ON C.CATEGORY_ID = MC.CATEGORY_ID WHERE MC.MEMBER_ID = M.MEMBER_ID) AS CATEGORIES " +
															 "FROM MEMBER M " +
															 "INNER JOIN SHIPPING_ADDRESS SA ON SA.MEMBER_ID = M.MEMBER_ID " +
															 "INNER JOIN GRADE G ON G.GRADE_ID = M.GRADE_ID " +
															 "WHERE SA.SHIPPING_DEFAULT = 1 " +
															 "AND M.MEMBER_STATE = 'JOIN' " +
															 "AND M.MEMBER_ID LIKE ?";
	
	// 로그인(내또코 회원) (+ 마이페이지PW확인)
	// 아이디와 비밀번호가 같은 행의 MEMBER_ID를 확인
	// MEMBER_STATE가 JOIN인지도 함께 확인
	// 일치하는 회원아이디와 권한(USER or ADMIN) 전달
	// 로그인시 MEMBER_STATE가 JOIN이 아닐 경우 로그인 실패
	private static final String SELECTONE_MEMBER_LOGIN = "SELECT MEMBER_ID, AUTHORITY FROM MEMBER "
														+ "WHERE MEMBER_ID = ? "
														+ "AND MEMBER_PASSWORD = ? "
														+ "AND MEMBER_STATE = 'JOIN'";
	
	// 한 멤버의 구체적인 정보를 습득하기 위한 쿼리
	private static final String SELECTONE_MEMBER_INFO = "SELECT m.MEMBER_NAME, m.PHONE_NUMBER, m.EMAIL, sa.SHIPPING_DEFAULT, sa.SHIPPING_POSTCODE, sa.SHIPPING_ADDRESS, sa.SHIPPING_DETAIL_ADDRESS " 
														+ "FROM MEMBER m "
													    + "JOIN SHIPPING_ADDRESS sa ON m.MEMBER_ID = sa.MEMBER_ID "
													    + "WHERE m.MEMBER_ID = ?";

	// 아이디 중복검사
	// 해당 아이디가 일치하는 행이 있다면 해당 행의 MEMBER_ID를 선택
	private static final String SELECTONE_MEMBER_ID_CHECK = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID = ?";
	

	   
	// 마이페이지의 회원정보 조회
	// SHIPPING_ADDRESS에서 기본값이 가장 큰 정보(기본배송지)를 조회
	private static final String SELECTONE_MEMBER_MYPAGE = "SELECT M.MEMBER_ID, M.MEMBER_NAME, M.GENDER, M.DAY_OF_BIRTH, M.PHONE_NUMBER, M.EMAIL, MAX_SA.SHIPPING_ADDRESS_ID, MAX_SA.SHIPPING_POSTCODE, MAX_SA.SHIPPING_ADDRESS, MAX_SA.SHIPPING_DETAIL_ADDRESS "
												        + "FROM MEMBER M "
												        + "JOIN ("
												        	+ "SELECT SA.MEMBER_ID, SA.SHIPPING_ADDRESS_ID, SA.SHIPPING_POSTCODE, SA.SHIPPING_ADDRESS, SA.SHIPPING_DETAIL_ADDRESS "
												        	+ "FROM SHIPPING_ADDRESS SA "
												        	+ "JOIN ("
												        		+ "SELECT MAX(SHIPPING_DEFAULT) AS MAX_SHIPPING_DEFAULT "
												        		+ "FROM SHIPPING_ADDRESS "
												        		+ "WHERE MEMBER_ID = ?) AS M_SA ON SA.SHIPPING_DEFAULT = M_SA.MAX_SHIPPING_DEFAULT "
												         + "WHERE SA.MEMBER_ID = ?) MAX_SA "
												         + "ON M.MEMBER_ID = MAX_SA.MEMBER_ID";

	
	// 회원가입
	// 회원가입에 필요한 데이터를 받아 DB에 추가한다
	private static final String INSERT_MEMBER = "INSERT INTO "
												+ "MEMBER (MEMBER_ID, MEMBER_NAME, MEMBER_PASSWORD, DAY_OF_BIRTH, GENDER, PHONE_NUMBER, EMAIL) "
												+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	// 현재 미지원 기능
	// 회원 탈퇴시 해당하는 MEMBER_ID의 MEMBER_STATE를 'LEAVE'로 변경
	// 해당 회원의 ID 삭제시 구매내역 등 여러 정보가 불일치될 수 있기때문에 회원 상태만 변경함
	private static final String UPDATE_MEMBER_STATE = "UPDATE MEMBER "
													+ "SET MEMBER_STATE = 'LEAVE' "
													+ "WHERE MEMBER_ID = ? ";
	
	
	// 개인정보 변경(이름, 전화번호, 이메일)
	// 해당 회원의 개인정보를 변경
	private static final String UPDATE_MEMBER_INFO = "UPDATE MEMBER SET MEMBER_NAME = ?, PHONE_NUMBER = ?, EMAIL = ? WHERE MEMBER_ID = ?";
	
	
	// 개인정보 변경(비밀번호)
	// 해당 회원의 개인정보를 변경
	private static final String UPDATE_MEMBER_PASSWORD = "UPDATE MEMBER SET MEMBER_PASSWORD = ? WHERE MEMBER_ID = ?";
	
	
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
		
		log.debug("selectAll 진입");
		
		if(memberDTO.getSearchCondition().equals("allMember")) {
			
			log.debug("allMember 진입");
			
			try {
			
				return jdbcTemplate.query(SELECTALL, new MemberRowMapper());
			
			} catch (Exception e) {
				
				log.error("allMember 예외 발생");

				return null;
			}
			
		} else if(memberDTO.getSearchCondition().equals("selectSubscriptionDatas")) {
			
			log.debug("selectSubscriptionDatas 진입");
			
			Object[] args = { memberDTO.getMemberID() };
			
			try {
				
				return jdbcTemplate.query(SELECTALL_SUBSCRIPTION_BY_MEMBER, args, new selectSubscriptionDatasRowMapper());
			
			} catch (Exception e) {
				
				log.error("selectSubscriptionDatas 예외 발생");

				return null;
				
			}
			
		} else if(memberDTO.getSearchCondition().equals("selectAdminMemberListDatas")) {
			
			log.debug("selectAdminMemberListDatas 진입");
			
			String sqlQuery = SELECTALL_JOIN_MEMBER_INFO;
			
			sqlQuery += " ORDER BY " + memberDTO.getSortColumnName() + " " + memberDTO.getSortMode();
			
			Object[] args = { memberDTO.getSearchKeyword() };
			
			try {
				
				return jdbcTemplate.query(sqlQuery, args, new joinMemberInfoRowMapper());
				
			} catch (Exception e) {
				
				log.error("selectAdminMemberListDatas 예외 발생");

				return null;
				
			}
			
		}
		
		log.error("selectAll 실패");
		
		return null;

	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public MemberDTO selectOne(MemberDTO memberDTO) {

		log.trace("selectOne 진입");

		if (memberDTO.getSearchCondition().equals("memberLogin")) {

			log.trace("memberLogin 진입");

			Object[] args = {memberDTO.getMemberID(), memberDTO.getMemberPassword()};

			try {

				return jdbcTemplate.queryForObject(SELECTONE_MEMBER_LOGIN, args, new MemberLoginRowMapper());

			} catch (Exception e) {

				log.error("memberLogin 실패");

				return null;
			}

		} else if(memberDTO.getSearchCondition().equals("selectMemberInfo")) {

			log.trace("selectMemberInfo 진입");

			Object[] args = {memberDTO.getMemberID()};

			try {

				return jdbcTemplate.queryForObject(SELECTONE_MEMBER_INFO, args, new SelectMemberInfoRowMapper());

			} catch (Exception e) {

				log.error("selectMemberInfo 예외/실패");

				return null;
			}

		} else if (memberDTO.getSearchCondition().equals("idDuplicationCheck")) {

			log.trace("idDuplicationCheck 진입");

			Object[] args = { memberDTO.getMemberID() };

			log.debug("오브젝트에 들어간 아이디 : " + args[0]);

			try {

				return jdbcTemplate.queryForObject(SELECTONE_MEMBER_ID_CHECK, args, new IdDuplicationCheckRowMapper());

			} catch (Exception e) {

				log.error("selectMemberInfo 에러/실패");

				return null;
			}

		} else if (memberDTO.getSearchCondition().equals("myPageMain")) {

			log.trace("myPageMain 진입");

			Object[] args = { memberDTO.getMemberID(), memberDTO.getMemberID() };

			log.debug("[Object[0]의 값] " + args[0]);
			log.debug("[Object[1]의 값] " + args[1]);

			try {

				return jdbcTemplate.queryForObject(SELECTONE_MEMBER_MYPAGE, args, new MyPageMainRowMapper());

			} catch (Exception e) {

				log.error("selectMemberInfo 에러/실패");

				return null;

			}

		}

		log.error("selectone 실패");
		return null;

	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public boolean insert(MemberDTO memberDTO) {
		log.trace("insert 진입");

		int result = 0;

		if (memberDTO.getSearchCondition().equals("join")) {

			log.trace("join 처리 진입");

			result = jdbcTemplate.update(INSERT_MEMBER, memberDTO.getMemberID(), memberDTO.getMemberName(),
										memberDTO.getMemberPassword(), memberDTO.getDayOfBirth(),
										memberDTO.getGender(), memberDTO.getPhoneNumber(),
										memberDTO.getEmail());

		}

		if (result <= 0) {

			log.error("join 처리 실패/에러");
			return false;

		}

		log.error("insert 성공");
		return true;

	}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	

	public boolean update(MemberDTO memberDTO) {
		
		log.trace("update 진입");

		if (memberDTO.getSearchCondition().equals("memberleave")) {

			log.trace("memberleave 진입");

			int result = jdbcTemplate.update(UPDATE_MEMBER_STATE, memberDTO.getMemberState());

			if (result <= 0) {

				log.error("memberleave update 실패");

				return false;

			}

			log.trace("memberleave update 성공");

			return true;

		} else if (memberDTO.getSearchCondition().equals("memberInfoUpdate")) {

			log.trace("memberInfoUpdate 진입");
			log.debug("[UPDATE] memberDTO 정보 : " + memberDTO.toString());

			int result = jdbcTemplate.update(UPDATE_MEMBER_INFO, memberDTO.getMemberName(), memberDTO.getPhoneNumber(), memberDTO.getEmail(), memberDTO.getMemberID());

			if (result <= 0) {

				log.error("UPDATE_MEMBER_INFO 실패");

				return false;

			}

			log.trace("UPDATE_MEMBER_INFO 성공");
			
			return true;
			
		} else if (memberDTO.getSearchCondition().equals("memberPasswordUpdate")) {

			log.trace("memberInfoUpdate 진입");
			log.debug("[UPDATE] memberDTO 정보 : " + memberDTO.toString());
			
			int result = jdbcTemplate.update(UPDATE_MEMBER_PASSWORD, memberDTO.getMemberPassword(), memberDTO.getMemberID());

			if (result <= 0) {

				log.error("UPDATE_MEMBER_INFO 실패");
				
				return false;

			}

			log.trace("UPDATE_MEMBER_INFO 성공");
			
			return true;
		}

		log.error("update 실패");
		return false;

	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(MemberDTO memberDTO) {

		return false;

	}
	
}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class MemberRowMapper implements RowMapper<MemberDTO> {

	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		log.trace("MemberRowMapper 진입");
		
		MemberDTO data = new MemberDTO();

		data.setMemberID(rs.getString("MEMBER_ID"));
		data.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
		data.setMemberName(rs.getString("MEMBER_NAME"));
		data.setDayOfBirth(rs.getDate("DAY_OF_BIRTH"));
		data.setGender(rs.getString("GENDER"));
		data.setPhoneNumber(rs.getString("PHONE_NUMBER"));
		data.setEmail(rs.getString("EMAIL"));
		data.setAuthority(rs.getString("AUTHORITY"));
		data.setMemberState(rs.getString("MEMBER_STATE"));

		log.debug(rs.getString("MEMBER_ID"));
		log.debug(rs.getString("MEMBER_PASSWORD"));
		log.debug(rs.getString("MEMBER_NAME"));
		log.debug(rs.getString("DAY_OF_BIRTH"));
		log.debug(rs.getString("GENDER"));
		log.debug(rs.getString("PHONE_NUMBER"));
		log.debug(rs.getString("EMAIL"));
		log.debug(rs.getString("AUTHORITY"));
		log.debug(rs.getString("MEMBER_STATE"));

		return data;

	}

}

@Slf4j
class MemberLoginRowMapper implements RowMapper<MemberDTO> {

	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		log.trace("MemberLoginRowMapper 진입");

		MemberDTO data = new MemberDTO();

		data.setMemberID(rs.getString("MEMBER_ID"));
		data.setAuthority(rs.getString("AUTHORITY"));

		log.debug(rs.getString("MEMBER_ID"));
		log.debug(rs.getString("AUTHORITY"));

		return data;

	}

}

@Slf4j
class SelectMemberInfoRowMapper implements RowMapper<MemberDTO> {

	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		log.trace("selectMemberInfoRowMapper 진입");

		MemberDTO data = new MemberDTO();

		data.setMemberName(rs.getString("m.MEMBER_NAME"));
		data.setPhoneNumber(rs.getString("m.PHONE_NUMBER"));
		data.setEmail(rs.getString("m.EMAIL"));
		data.setAncShippingDefault(rs.getInt("sa.SHIPPING_DEFAULT"));
		data.setAncShippingPostCode(rs.getInt("sa.SHIPPING_POSTCODE"));
		data.setAncShippingAddress(rs.getString("sa.SHIPPING_ADDRESS"));
		data.setAncShippingAddressDetail(rs.getString("sa.SHIPPING_DETAIL_ADDRESS"));

		return data;

	}

}

@Slf4j
class IdDuplicationCheckRowMapper implements RowMapper<MemberDTO> {

	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		log.trace("IdDuplicationCheckRowMapper 진입");

		MemberDTO data = new MemberDTO();

		data.setMemberID(rs.getString("MEMBER_ID"));

		return data;

	}

}

@Slf4j
class selectSubscriptionDatasRowMapper implements RowMapper<MemberDTO> {

	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		log.trace("selectSubscriptionDatasRowMapper 진입");

		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setAncSubcriptionInfoID(rs.getInt("SI.SUBSCRIPTION_INFO_ID"));
		memberDTO.setAncBeginDate(rs.getTimestamp("SI.BEGIN_DATE"));
		memberDTO.setAncSubscriptionTimes(rs.getInt("SI.SUBSCRIPTION_TIMES"));
		memberDTO.setAncNextPaymentDate(rs.getTimestamp("SI.NEXT_PAYMENT_DATE"));
		memberDTO.setAncSubscriptionPostcode(rs.getInt("SI.SUBSCRIPTION_POSTCODE"));
		memberDTO.setAncSubscriptionAddress(rs.getString("SI.SUBSCRIPTION_ADDRESS"));
		memberDTO.setAncSubscriptionDetailAddress(rs.getString("SI.SUBSCRIPTION_DETAIL_ADDRESS"));
		memberDTO.setAncSubscriptionClosingTimes(rs.getInt("SI.SUBSCRIPTION_CLOSING_TIMES"));
		memberDTO.setAncProductID(rs.getInt("SIP.PRODUCT_ID"));
		memberDTO.setAncQuantity(rs.getInt("SIP.QUANTITY"));
		memberDTO.setAncPurchasePrice(rs.getInt("SIP.PURCHASE_PRICE"));
		
		log.trace("selectSubscriptionDatasRowMapper 완료");
		
		return memberDTO;

	}

}


@Slf4j
class MyPageMainRowMapper implements RowMapper<MemberDTO> {

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

      log.trace("myPageMainRowMapper 진입");

      MemberDTO memberDTO = new MemberDTO();
      
      memberDTO.setMemberName(rs.getString("M.MEMBER_NAME"));
      memberDTO.setGender(rs.getString("M.GENDER"));
      memberDTO.setDayOfBirth(rs.getDate("M.DAY_OF_BIRTH"));
      memberDTO.setPhoneNumber(rs.getString("M.PHONE_NUMBER"));
      memberDTO.setEmail(rs.getString("M.EMAIL"));
      memberDTO.setAncShippingAddressID(rs.getInt("MAX_SA.SHIPPING_ADDRESS_ID"));
      memberDTO.setAncShippingPostCode(rs.getInt("MAX_SA.SHIPPING_POSTCODE"));
      memberDTO.setAncShippingAddress(rs.getString("MAX_SA.SHIPPING_ADDRESS"));
      memberDTO.setAncShippingAddressDetail(rs.getString("MAX_SA.SHIPPING_DETAIL_ADDRESS"));
      
      log.debug("[MyPageMainRowMapper] memberDTO 정보 : " + memberDTO.getPhoneNumber());

      log.trace("myPageMainRowMapper 완료");
      
      return memberDTO;

   }

}

@Slf4j
class joinMemberInfoRowMapper implements RowMapper<MemberDTO> {

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

      log.debug("joinMemberInfoRowMapper 진입");

      MemberDTO memberDTO = new MemberDTO();
      
      memberDTO.setMemberID(rs.getString("M.MEMBER_ID"));
      memberDTO.setMemberName(rs.getString("MEMBER_NAME"));
      memberDTO.setDayOfBirth(rs.getDate("M.DAY_OF_BIRTH"));
      memberDTO.setGender(rs.getString("M.GENDER"));
      memberDTO.setPhoneNumber(rs.getString("M.PHONE_NUMBER"));
      memberDTO.setEmail(rs.getString("M.EMAIL"));
      memberDTO.setAncShippingPostCode(rs.getInt("SA.SHIPPING_POSTCODE"));
      memberDTO.setAncShippingAddress(rs.getString("SA.SHIPPING_ADDRESS"));
      memberDTO.setAncShippingAddressDetail(rs.getString("SA.SHIPPING_DETAIL_ADDRESS"));
      memberDTO.setAncGradeName(rs.getString("G.GRADE_NAME"));
      memberDTO.setAncCategoryName(rs.getString("CATEGORIES"));
      
      log.debug("joinMemberInfoRowMapper 완료");
      
      return memberDTO;

   }

}

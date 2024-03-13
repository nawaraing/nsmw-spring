package com.naeddoco.nsmwspring.legacyModel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;
import com.naeddoco.nsmwspring.legacyModel.util.JDBCUtil;

public class MemberDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	// (관) 회원목록
	private static final String SELECTALL_MEMBER = "";

	// 로그인(내또코 회원) + 마이페이지PW확인
	// 아이디와 비밀번호가 같은 행의 아이디를 선택
	private static final String SELECTONE_MEMBER_LOGIN = "SELECT M_ID FROM MEMBER WHERE M_ID=? AND M_PASSWORD = ?";
	
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
	private static final String UPDATE_MEMBER_INFO = "UPDATE MEMBER "
			+ "SET "
			+ "M_NAME = ?, "
			+ "PHONE_NUMBER = ?, "
			+ "EMAIL = ?, "
			+ "M_POSTCODE = ?, "
			+ "M_ADDRESS = ?, "
			+ "M_DETAILED_ADDRESS = ? "
			+ "WHERE M_ID = ?";

	// 비밀번호번경
	// 해당 회원의 비밀번호를 변경한다
	private static final String UPDATE_MEMBER_PW = "UPDATE MEMBER SET M_PASSWORD=? WHERE M_ID=?";

	// 회원탈퇴
	// 해당회원을 DB에서 제거한다
	private static final String DELETE_MEMBER = "DELETE FROM MEMBER WHERE M_ID = ?";
	
	
	
	private MemberDTO selectAll(MemberDTO mDTO) {
		return null;
	}

	public MemberDTO selectOne(MemberDTO mDTO) {

		// 결과를 반환할 때 사용하는 객제 정의
		MemberDTO memberDTO = null;

		// DB 연결
		conn = JDBCUtil.connect();

		// 입력한 ID가 DB에 있는지 확인
		if (mDTO.getSearchCondition().equals("아이디중복검사")) {
			
			System.out.println("[MODEL_로그_아이디중복검사] 진입");
			
			// 데이터를 저장할 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_ID_CHECK);
				pstmt.setString(1, mDTO.getMID());
				System.out.println("[MODEL_로그_아이디중복검사] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_아이디중복검사] 쿼리문 실행");

				// 결과가 있다면 객체에 데이터 저장
				if (rs.next()) {
					memberDTO.setMID(rs.getString("M_ID"));
				} else {
					memberDTO = null;
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_아이디중복검사] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 쿼리문 실행 결과 성공 시 객체 반환
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_아이디중복검사] 성공");
				return memberDTO;
			}
		}
		// ID와 PW를 입력받아 DB에 확인
		else if (mDTO.getSearchCondition().equals("로그인")) {
			
			System.out.println("[MODEL_로그_로그인] 진입");
			
			// 데이터를 저장할 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_LOGIN);
				pstmt.setString(1, mDTO.getMID());
				pstmt.setString(2, mDTO.getmPassword());
				System.out.println("[MODEL_로그_로그인] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_로그인] 쿼리문 실행");

				// 쿼리문 실행 결과가 있다면 객체에 데이터 저장
				if (rs.next()) {
					memberDTO.setMID(rs.getString("M_ID"));
				} else {
					memberDTO = null;
				}

				//ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_로그인] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 객체에 데이터가 들어있다면
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_로그인] 성공");
				return memberDTO;
			}
		} 
		
		else if (mDTO.getSearchCondition().equals("카카오로그인")) {
			
			System.out.println("[MODEL_로그_카카오로그인] 진입");
			
			// 데이터를 저장할 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_KAKAO_LOGIN);
				pstmt.setString(1, mDTO.getKakaoId());
				System.out.println("[MODEL_로그_카카오로그인] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_카카오로그인] 쿼리문 실행");

				// 쿼리문 실행 결과가 있다면
				if (rs.next()) {
					memberDTO.setMID(rs.getString("M_ID"));
				} else {
					memberDTO = null;
				}

				//ResultSet 객체 종료
				rs.close();

				//예외 처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_카카오로그인] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 객체가 null이 아니라면 성공 반환
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_카카오로그인] 성공");
				return memberDTO;
			}
		} 
		// 개인정보 변경 진입 시 비밀번호 확인
		else if (mDTO.getSearchCondition().equals("비밀번호확인")) {
			
			System.out.println("[MODEL_로그_비밀번호확인] 진입");
			
			// 결과를 반환할 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_PW_CHECK);
				pstmt.setString(1, mDTO.getMID());
				pstmt.setString(2, mDTO.getmPassword());
				System.out.println("[MODEL_로그_비밀번호확인] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_비밀번호확인] 쿼리문 실행");

				// 쿼리문 실행 결과가 있다면 객체에 데이터 저장
				if (rs.next()) {
					memberDTO.setMID(rs.getString("M_ID"));
					memberDTO.setmName(rs.getString("M_NAME"));
				} else {
					memberDTO = null;
				}

				// ResultSet객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_비밀번호확인] 예외처리");
				e.printStackTrace();
				return null;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 객체가 null이 아니라면 성공
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_비밀번호확인] 성공");
				return memberDTO;
			}
		}
		// 마이페이지에 기본적으로 출력할 정보를 DB에서 받아옴
		else if (mDTO.getSearchCondition().equals("회원정보")) {
			
			System.out.println("[MODEL_로그_회원정보] 진입");
			
			// 데이터 저장후 반환에 사용할 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_MYPAGE);
				pstmt.setString(1, mDTO.getMID());
				System.out.println("[MODEL_로그_회원정보] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_회원정보] 쿼리문 실행");

				// 쿼리문 실행결과가 있다면 진행
				if (rs.next()) {
					memberDTO.setmName(rs.getString("M_NAME"));
					memberDTO.setDob(rs.getDate("DOB"));
					memberDTO.setGender(rs.getString("GENDER"));
					memberDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));
					memberDTO.setEmail(rs.getString("EMAIL"));
					memberDTO.setmPostCode(rs.getInt("M_POSTCODE"));
					memberDTO.setmAddress(rs.getString("M_ADDRESS"));
					memberDTO.setmDetailedAddress(rs.getString("M_DETAILED_ADDRESS"));
					memberDTO.setHealth(rs.getString("HEALTH"));
				} else {
					memberDTO = null;
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_회원정보] 예외처리");
				e.printStackTrace();
				return null;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 객체가 null이 아니라면 성공 반환
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_회원정보] 성공");
				return memberDTO;
			}
		}
		// 회원의 건강상태를 반환하는 기능
		else if (mDTO.getSearchCondition().equals("건강상태")) {
			
			System.out.println("[MODEL_로그_건강상태] 진입");
			
			// 데이터를 저장하여 반환될 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_HEALTH);
				pstmt.setString(1, mDTO.getMID());
				System.out.println("[MODEL_로그_건강상태] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_건강상태] 쿼리문 실행");

				// 쿼리문 실행결과가 있다면 진행
				if (rs.next()) {
					memberDTO.setHealth(rs.getString("HEALTH"));
				} else {
					memberDTO = null;
				}

				//ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_건강상태] 예외처리");
				e.printStackTrace();
				return null;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 객체가 null이 아니라면 성공 반환
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_건강상태] 성공");
				return memberDTO;
			}
			// 상품 주문에 필요한 회원정보를 받아오는 기능
		} else if (mDTO.getSearchCondition().equals("주문정보")) {
			
			System.out.println("[MODEL_로그_주문정보] 진입");

			// 데이터를 저장후 반환될 객체 생성
			memberDTO = new MemberDTO();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_BUYPAGE);
				pstmt.setString(1, mDTO.getMID());
				System.out.println("[MODEL_로그_주문정보] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_주문정보] 쿼리문 실행");

				// 쿼리문 실행 결과가 있다면 객체에 데이터 저장
				if (rs.next()) {
					memberDTO.setmName(rs.getString("M_NAME"));
					memberDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));
					memberDTO.setmPostCode(rs.getInt("M_POSTCODE"));
					memberDTO.setmAddress(rs.getString("M_ADDRESS"));
					memberDTO.setmDetailedAddress(rs.getString("M_DETAILED_ADDRESS"));
					memberDTO.setEmail(rs.getString("EMAIL"));
				} else {
					memberDTO = null;
				}

				// ResultSet객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_주문정보] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 객체가 null이 아니라면 성공 반환
			if (memberDTO != null) {
				System.out.println("[MODEL_로그_주문정보] 성공");
				return memberDTO;
			}
		} 
		// selectOne 실패시 null 반환
		System.out.println("[MODEL_로그_회원]selectOne 실패");
		return null;
	}

	public boolean insert(MemberDTO mDTO) {

		// 쿼리문 실행 결과를 저장하기 위한 변수
		int result = 0;
		
		// DB연결
		conn = JDBCUtil.connect();

		// 회원가입에 필요한 정보를 받아 DB에 추가하는 기능
		if (mDTO.getSearchCondition().equals("회원가입")) {
			
			System.out.println("[MODEL_로그_회원가입] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(INSERT_MEMBER);
				pstmt.setString(1, mDTO.getMID());
				pstmt.setString(2, mDTO.getmName());
				pstmt.setString(3, mDTO.getmPassword());
				pstmt.setDate(4, mDTO.getDob());
				pstmt.setString(5, mDTO.getGender());
				pstmt.setString(6, mDTO.getPhoneNumber());
				pstmt.setString(7, mDTO.getEmail());
				pstmt.setInt(8, mDTO.getmPostCode());
				pstmt.setString(9, mDTO.getmAddress());
				pstmt.setString(10, mDTO.getmDetailedAddress());
				pstmt.setString(11, mDTO.getHealth());
				pstmt.setString(12, mDTO.getLoginType());
				pstmt.setString(13, mDTO.getKakaoId());
				System.out.println("[MODEL_로그_회원가입] 쿼리문 set");
				
				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_회원가입] 쿼리문 성공");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_회원가입] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행결과가 있다면 true반환
			if (result > 0) {
				System.out.println("[MODEL_로그_회원가입] 성공");
				return true;
			}
			
		}
		// insert실패시 false반환
		System.out.println("[MODEL_로그_회원]insert 실패");
		return false;
	}

	// 회원정보 변경
	public boolean update(MemberDTO mDTO) {
		
		// 쿼리문 실행 결과를 저장하기 위한 변수
		int result = 0;

		// DB연결
		conn = JDBCUtil.connect();

		// 회원정보를 변경하는 기능(이름, 전화번호, 이메일, 주소)
		if (mDTO.getSearchCondition().equals("회원정보변경")) {
			
			System.out.println("[MODEL_로그_회원정보변경] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(UPDATE_MEMBER_INFO);
				pstmt.setString(1, mDTO.getmName());
				pstmt.setString(2, mDTO.getPhoneNumber());
				pstmt.setString(3, mDTO.getEmail());
				pstmt.setInt(4, mDTO.getmPostCode());
				pstmt.setString(5, mDTO.getmAddress());
				pstmt.setString(6, mDTO.getmDetailedAddress());
				pstmt.setString(7, mDTO.getMID());
				System.out.println("[MODEL_로그_회원정보변경] 쿼리 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_회원정보변경] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_회원정보변경] 예외처리");
				e.printStackTrace();
				return false;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행 결과가 있다면 성공 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_회원정보변경] 성공");
				return true;
			}
			
		}

		// 회원의 비밀번호만 변경하는 기능
		else if (mDTO.getSearchCondition().equals("비밀번호변경")) {
			
			System.out.println("[MODEL_로그_비밀번호변경] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(UPDATE_MEMBER_PW);
				pstmt.setString(1, mDTO.getmPassword());
				pstmt.setString(2, mDTO.getMID());
				System.out.println("[MODEL_로그_비밀번호변경] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_비밀번호변경] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_비밀번호변경] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 쿼리문 실행 결과가 있다면 성공 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_비밀번호변경] 성공");
				return true;
			}
			
		}
		// update 실패시 false 반환
		System.out.println("[MODEL_로그_회원]update 실패");
		return false;
	}

	// 회원탈퇴
	public boolean delete(MemberDTO mDTO) {
		
		// 쿼리문 실행결과를 저장하기위한 변수
		int result = 0;
		
		//DB연결 
		conn = JDBCUtil.connect();

		// 해당 회원을 DB에서 제거하기 위한 기능
		if (mDTO.getSearchCondition().equals("회원탈퇴")) {
			
			System.out.println("[MODEL_로그_회원탈퇴] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(DELETE_MEMBER);
				pstmt.setString(1, mDTO.getMID());
				System.out.println("[MODEL_로그_회원탈퇴] 쿼리문 set");

				// 쿼리문 준비 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_회원탈퇴] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_회원탈퇴] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행결과가 있다면 true반환
			if (result > 0) {
				System.out.println("[MODEL_로그_회원탈퇴] 성공");
				return true;
			}
			
		}
		// delete실패시 false 반환
		System.out.println("[MODEL_로그_회원] 실패");
		return false;
	}
}
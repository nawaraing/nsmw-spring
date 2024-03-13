package com.naeddoco.nsmwspring.legacyModel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import com.naeddoco.nsmwspring.legacyModel.dto.CouponDTO;
import com.naeddoco.nsmwspring.legacyModel.util.JDBCUtil;

public class CouponDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	// 마이페이지에서 보유한 쿠폰을 보여주는 쿼리
	// 쿠폰 테이블에서 해당 회원의 쿠폰 전부를 선택하여
	// 사용 -> 미사용, 유효기간 만료일 순으로 정렬한다
	private static final String SELECTALL_COUPON_MYPAGE = "SELECT CP_ID, M_ID, CP_NAME, PERIOD, DISCOUNT, CATEGORY, USED "
			+ "FROM COUPON " + "WHERE M_ID = ? " + "ORDER BY USED DESC, PERIOD ASC";

	// 상품 구매할 때 사용 가능한 쿠폰을 보여주는 쿼리
	// 해당 회원의 쿠폰 중 미사용한 쿠폰만 선택
	private static final String SELECTALL_COUPON_USECP = "SELECT CP_ID, M_ID, CP_NAME, PERIOD, DISCOUNT, CATEGORY, USED "
			+ "FROM COUPON " + "WHERE M_ID = ? AND USED = '미사용' " + "ORDER BY PERIOD ASC";

	// 쿠폰 하나의 상세정보는 사용하지 않는다
	private static final String SELECTONE_COUPON = "";

	// (관) 사용자에게 쿠폰을 지급하는 관리자 기능
	// 유효기간을 지정해서 부여한다
	private static final String INSERT_COUPON = "INSERT INTO COUPON (CP_ID, M_ID, CP_NAME, PERIOD, DISCOUNT, CATEGORY) "
			+ "VALUES ("
			+ "?, "
			+ "?, "
			+ "?, "
			+ "SYSTIMESTAMP + INTERVAL '30' DAY, "
			+ "?, "
			+ "?)";

	// 회원이 쿠폰을 사용해서 결재를 했을 때 쿠폰의 사용여부를 변경하는 쿼리
	// 해당 쿠폰의 사용여부를 '사용'으로 변경한다
	private static final String UPDATE_COUPON = "UPDATE COUPON SET USED = '사용' WHERE CP_ID = ?";

	// 미사용 쿠폰 중 유효기간이 지난 쿠폰을 삭제한다
	private static final String DELETE_COUPON = "DELETE FROM COUPON WHERE USED = '미사용' AND PERIOD < SYSTIMESTAMP";

	public ArrayList<CouponDTO> selectAll(CouponDTO cpDTO) {

		// 회원에게 보여줄 쿠폰들을 저장해서 반환할 리스트 객체 정의
		ArrayList<CouponDTO> couponList = null;
		// 리스트에 저장할 쿠폰 객체 정의
		CouponDTO couponDTO = null;

		// DB 연결
		conn = JDBCUtil.connect();

		// 해당 회원의 쿠폰목록(사용 과 미사용)을 선택해서 저장하는 코드
		if (cpDTO.getSearchCondition().equals("쿠폰목록")) {

			System.out.println("[MODEL_로그_쿠폰목록] 진입");

			// 쿠폰리스트를 생성
			couponList = new ArrayList<>();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTALL_COUPON_MYPAGE);
				pstmt.setString(1, cpDTO.getMID());
				System.out.println("[MODEL_로그_쿠폰목록] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_쿠폰목록] 쿼리 실행 성공");

				// 실행결과가 있다면 실행
				while (rs.next()) {

					// 쿠폰 하나의 정보를 저장할 객체 생성
					couponDTO = new CouponDTO();

					// 밀리초 단위까지 나오는 자료형의 값을 분까지 표현하기 위한 코드
					// 밀리초 단위로 값을 받아옴
					couponDTO.setPeriod(rs.getTimestamp("PERIOD"));
					// 받아온 값을 변경할 형식(포멧) 지정
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					// 형식을 변경한 값을 변수에 저장
					String period = timeFormat.format(couponDTO.getPeriod());

					couponDTO.setCPID(rs.getString("CP_ID"));
					couponDTO.setMID(rs.getString("M_ID"));
					couponDTO.setCpName(rs.getString("CP_NAME"));
					couponDTO.setPeriod(rs.getTimestamp("PERIOD"));
					couponDTO.setAncPeriod(period);
					couponDTO.setDiscount(rs.getInt("DISCOUNT"));
					couponDTO.setCategory(rs.getString("CATEGORY"));
					couponDTO.setUsed(rs.getString("USED"));

					// 쿠폰 하나의 정보가 전부 저장된 couponDTO를 리스트에 추가
					couponList.add(couponDTO);
				}

				// ResultSet객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_쿠폰목록] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 쿼리 실행 결과 성공 시 리스트 반환
			if (couponList.size() > 0) {
				System.out.println("[MODEL_로그_쿠폰목록] 성공");
				return couponList;
			}

			// 만료일 순으로 미사용인 쿠폰만 선택
		} else if (cpDTO.getSearchCondition().equals("사용가능쿠폰")) {
			
			System.out.println("[MODEL_로그_사용가능쿠폰] 진입");
			
			// 쿠폰리스트 생성
			couponList = new ArrayList<>();

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTALL_COUPON_USECP);
				pstmt.setString(1, cpDTO.getMID());
				System.out.println("[MODEL_로그_사용가능쿠폰] 쿼리문 set");

				// 쿼리 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_사용가능쿠폰] 쿼리문 실행");

				// 행이 없을 때 까지 반복
				while (rs.next()) {

					// 쿠폰 정보를 저장할 객체 생성
					couponDTO = new CouponDTO();

					// 사용자에게 분까지 보여주기 위해 데이터 형식 변경
					couponDTO.setPeriod(rs.getTimestamp("PERIOD"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String period = timeFormat.format(couponDTO.getPeriod());

					couponDTO.setCPID(rs.getString("CP_ID"));
					couponDTO.setMID(rs.getString("M_ID"));
					couponDTO.setCpName(rs.getString("CP_NAME"));
					couponDTO.setPeriod(rs.getTimestamp("PERIOD"));
					couponDTO.setAncPeriod(period);
					couponDTO.setDiscount(rs.getInt("DISCOUNT"));
					couponDTO.setCategory(rs.getString("CATEGORY"));
					couponDTO.setUsed(rs.getString("USED"));
					
					// 쿠폰 하나의 데이터가 담긴 couponDTO를 리스트에 추가 
					couponList.add(couponDTO);
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_사용가능쿠폰] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행 결과 성공 시 리스트 반환
			if (couponList.size() > 0) {
				System.out.println("[MODEL_로그_사용가능쿠폰] 성공");
				return couponList;
			}
			
		}
		System.out.println("[MODEL_로그_사용가능쿠폰]selectAll 실패");
		return null;
	}

	private CouponDTO selectOne(CouponDTO cpDTO) {
		return null;
	}

	public boolean insert(CouponDTO cpDTO) {
		
		// 쿼리문 실행 결과를 저장하는 변수
		int result = 0;

		// DB연결
		conn = JDBCUtil.connect();

		// 영문 대문자와 숫자를 혼합한 10자리 랜덤 쿠폰번호를 생성해 DB에 쿠폰을 추가하는 기능
		if (cpDTO.getSearchCondition().equals("쿠폰추가")) {
			
			System.out.println("[MODEL_로그_쿠폰추가] 진입");

			// 쿠폰 자리수 지정
			int couponSize = 10;

			// 쿠폰에 들어갈 조건 지정(영문 대문자, 숫자)
			String couponString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

			// 쿠폰번호를 저장할 변수
			StringBuilder couponNum = new StringBuilder(couponSize);

			// 랜덤클래스 객체 정의 및 생성
			Random rand = new Random();

			// 0~(10-1)번 반복하는 반복문
			for (int i = 0; i < couponSize; i++) {

				// 변수에 couponString의 길이(0~35)로 난수를 생성해 저장
				int randomIndex = rand.nextInt(couponString.length());

				// 난수의 위치에 있는 문자를 변수에 저장
				char randomChar = couponString.charAt(randomIndex);

				// 난수의 위치에 있던 문자를 couponNum(StringBuilder)에 추가
				couponNum.append(randomChar);
			}

			System.out.println("[MODEL_로그_쿠폰추가] 쿠폰번호 생성 : "+couponNum.toString());

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(INSERT_COUPON);
				pstmt.setString(1, couponNum.toString());
				pstmt.setString(2, cpDTO.getMID());
				pstmt.setString(3, cpDTO.getCpName());
				pstmt.setInt(4, cpDTO.getDiscount());
				pstmt.setString(5, cpDTO.getCategory());
				System.out.println("[MODEL_로그_쿠폰추가] 쿼리문 set");

				// 쿼리 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_쿠폰추가] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_쿠폰추가] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행결과가 있다면 true 반환
			if (result <= 0) {
				System.out.println("[MODEL_로그_쿠폰추가] 성공");
				return true;
			}
			
		}
		// insert 실패시 false 반환
		System.out.println("[MODEL_로그_쿠폰]insert 실패");
		return false;
	}

	public boolean update(CouponDTO cpDTO) {
		
		// 쿼리문 실행결과를 저장할 변수
		int result = 0;

		// DB 연결
		conn = JDBCUtil.connect();

		// 사용한 쿠폰의 사용여부를 변경
		if (cpDTO.getSearchCondition().equals("쿠폰사용")) {
			
			System.out.println("[MODEL_로그_쿠폰사용] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(UPDATE_COUPON);
				pstmt.setString(1, cpDTO.getCPID());
				System.out.println("[MODEL_로그_쿠폰사용] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_쿠폰사용] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_쿠폰사용] 예외처리");
				e.printStackTrace();
				return false;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행 결과 성공 시 true반환
			if (result > 0) {
				System.out.println("[MODEL_로그_쿠폰사용] 성공");
				return true;
			}
			
		}
		// update 실패시 false 반환
		System.out.println("[MODEL_로그_쿠폰사용]update 실패");
		return false;
	}

	public boolean delete(CouponDTO cpDTO) {

		// 쿼리문 실행결과를 저장할 변수
		int result = 0;
		
		// DB연결
		conn = JDBCUtil.connect();

		// 사용하지 않은 쿠폰 중 유효기간이 지난 쿠폰 삭제
		if (cpDTO.getSearchCondition().equals("쿠폰삭제")) {
			
			System.out.println("[MODEL_로그_쿠폰삭제] 진입");

			// 예외처리
			try {
				// 쿼리문 준비
				pstmt = conn.prepareStatement(DELETE_COUPON);
				System.out.println("[MODEL_로그_쿠폰삭제] 쿼리문 set");

				// 쿼리문 실행 및 결과저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_쿠폰삭제] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_쿠폰삭제] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행결과 성공 시 true 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_쿠폰삭제] 성공");
				return true;
			}
			
		}
		// delete 실패 시 false반환
		System.out.println("[MODEL_로그_쿠폰삭제]delete 실패");
		return false;
	}
}

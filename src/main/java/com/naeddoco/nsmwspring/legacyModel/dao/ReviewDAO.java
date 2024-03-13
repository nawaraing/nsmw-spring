package com.naeddoco.nsmwspring.legacyModel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyModel.dto.ReviewDTO;
import com.naeddoco.nsmwspring.legacyModel.util.JDBCUtil;

public class ReviewDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	// 내 리뷰
	// 마이페이지 내뷰 내역에 해당 회원이 작성한 리뷰를 출력해주기 위한 쿼리
	// 어떤 상품의 리뷰인지 알려주기 위해 조인을 사용
	// 리뷰는 상품과 조인을 할 수 없어 구매내역과 먼저 조인 후 상품테이블과 조인
	// 가장 최근에 작성한 리뷰 순서로 정렬
	private static final String SELECTALL_REVIEW_MEMBER = "SELECT R.R_ID, R.M_ID, R.B_ID, R.SCORE, R.CONTENTS, R.CREATE_TIME, B.P_ID, P.P_NAME, P.IMAGE_PATH "
			+ "FROM REVIEW R " + "JOIN BUYINFO B ON R.B_ID = B.B_ID " + "JOIN PRODUCT P ON B.P_ID = P.P_ID "
			+ "WHERE R.M_ID = ? " + "ORDER BY R.CREATE_TIME DESC";

	// 상품 리뷰
	// 상품 세부정보페이지의 하단에 해당 상품에 대한 리뷰를 출력
	// 최근 리뷰 순으로 정렬
	private static final String SELECTALL_REVIEW_PRODUCT = "SELECT R.R_ID, R.M_ID, R.B_ID, R.SCORE, R.CONTENTS, R.CREATE_TIME, B.P_ID, P.P_NAME, R.IMAGENAME "
			+ "FROM REVIEW R " + "JOIN BUYINFO B ON B.B_ID = R.B_ID " + "JOIN PRODUCT P ON B.P_ID = P.P_ID "
			+ "WHERE B.P_ID =? " + "ORDER BY R.CREATE_TIME DESC";

	// 리뷰 상세
	// 작성된 리뷰 중 하나를 선택해서 보여주기 위한 쿼리
	// 리뷰 작성자를 회원 ID가 아닌 회원 이름으로 보여주기 위해 회원과 추가로 조인
	private static final String SELECTONE_REVIEW_DETAIL = "SELECT R.R_ID, R.M_ID, R.B_ID, R.SCORE, R.CONTENTS, R.CREATE_TIME, B.P_ID, P.P_NAME, M.M_NAME, M.EMAIL, R.IMAGENAME "
			+ "FROM REVIEW R " + "JOIN BUYINFO B ON B.B_ID = R.B_ID " + "JOIN PRODUCT P ON B.P_ID = P.P_ID "
			+ "JOIN MEMBER M ON R.M_ID = M.M_ID " + "WHERE R.R_ID = ? ";

	// 상품 별점
	// 해당 상품의 별점의 평균을 구해서 반환하기 위한 쿼리
	private static final String SELECTONE_REVIEW_AVG_SCORE = "SELECT AVG(R.SCORE) AS AVG_SCORE " + "	FROM REVIEW R "
			+ "	INNER JOIN BUYINFO B ON R.B_ID = B.B_ID " + "	WHERE B.P_ID = ?";

	// 리뷰작성
	// 리뷰작성에 필요한 정보를 전달받아 DB에 추가하는 쿼리
	private static final String INSERT_REVIEW = "INSERT INTO REVIEW "
			+ "(R_ID, M_ID, B_ID, SCORE, CONTENTS, CREATE_TIME, IMAGENAME) " + "VALUES( "
			+ "    NVL((SELECT MAX(R_ID) FROM REVIEW), 0) + 1, " + "    ?, " + "    ?, " + "    ?, " + "    ?, "
			+ "    CURRENT_TIMESTAMP, " + "    ?" + ")";

	// 수정불가
	private static final String UPDATE_REVIEW = "";

	// 리뷰삭제
	// 선택한 리뷰를 삭제하는 쿼리
	private static final String DELETE_REVIEW = "DELETE FROM REVIEW WHERE R_ID = ?";

	public ArrayList<ReviewDTO> selectAll(ReviewDTO rDTO) {

		// 데이터를 저장해서 반환될 객체 정의
		ArrayList<ReviewDTO> reviewList = null;
		ReviewDTO reviewDTO = null;

		// DB연결
		conn = JDBCUtil.connect();

		// 해당 회원이 작성한 모든 리뷰를 출력하기 위한 기능
		if (rDTO.getSearchCondition().equals("내리뷰")) {

			System.out.println("[MODEL_로그_내리뷰] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTALL_REVIEW_MEMBER);
				pstmt.setString(1, rDTO.getMID());
				System.out.println("[MODEL_로그_내리뷰] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_내리뷰] 쿼리문 실행");

				// 리뷰를 여러개 저장할 리스트 객체 생성
				reviewList = new ArrayList<ReviewDTO>();

				// 쿼리문 실행 결과가 있을 경우 실행
				while (rs.next()) {

					// 리뷰 하나의 데이터를 저장할 객체 생성
					reviewDTO = new ReviewDTO();

					// 리뷰 작성일을 분까지만 표시하기 위해 형식 변환
					reviewDTO.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String createTime = timeFormat.format(reviewDTO.getCreateTime());

					// 객체에 리뷰 하나의 데이터를 저장
					reviewDTO.setRID(rs.getInt("R_ID"));
					reviewDTO.setMID(rs.getString("M_ID"));
					reviewDTO.setBID(rs.getInt("B_ID"));
					reviewDTO.setScore(rs.getInt("SCORE"));
					reviewDTO.setContents(rs.getString("CONTENTS"));
					reviewDTO.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					reviewDTO.setAncCreateTime(createTime);
					reviewDTO.setAncPID(rs.getInt("P_ID"));
					reviewDTO.setAncPName(rs.getString("P_NAME"));
					reviewDTO.setAncImagePath(rs.getString("IMAGE_PATH"));
					// 리뷰 하나의 데이터가 저장된 객체를 리스트에 저장
					reviewList.add(reviewDTO);
				}

				// ResultSet 객체 종료
				rs.close();

			} catch (SQLException e) {
				System.out.println("[MODEL_로그_내리뷰] 예외처리");
				e.printStackTrace();
				return null;
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 리뷰가 하나도 있을 경우 성공
			if (reviewList.size() > 0) {
				System.out.println("[MODEL_로그_내리뷰] 성공");
				return reviewList;
			}

			// 해당 상품의 모든 리뷰
		} else if (rDTO.getSearchCondition().equals("상품리뷰")) {

			System.out.println("[MODEL_로그_상품리뷰] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTALL_REVIEW_PRODUCT);
				pstmt.setInt(1, rDTO.getAncPID());
				System.out.println("[MODEL_로그_상품리뷰] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_상품리뷰] 쿼리문 실행");

				// 여러개의 리뷰를 저장할 수 있는 리스트 객체 생성
				reviewList = new ArrayList<ReviewDTO>();

				// 쿼리문 실행 결과가 있으면 실행
				while (rs.next()) {

					// 리뷰 하나의 데이터를 저장할 객체 생성
					reviewDTO = new ReviewDTO();

					// 리뷰 작성일을 분까지만 표시하기위해 형식변환
					reviewDTO.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String createTime = timeFormat.format(reviewDTO.getCreateTime());

					// 리뷰 하나의 데이터를 객체에 저장
					reviewDTO.setRID(rs.getInt("R_ID"));
					reviewDTO.setMID(rs.getString("M_ID"));
					reviewDTO.setBID(rs.getInt("B_ID"));
					reviewDTO.setScore(rs.getInt("SCORE"));
					reviewDTO.setContents(rs.getString("CONTENTS"));
					reviewDTO.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					reviewDTO.setAncCreateTime(createTime);
					reviewDTO.setAncPID(rs.getInt("P_ID"));
					reviewDTO.setAncPName(rs.getString("P_NAME"));
					reviewDTO.setImageName(rs.getString("IMAGENAME"));
					// 하나의 리뷰 데이터가 저장된 객체를 리스트에 저장
					reviewList.add(reviewDTO);
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_상품리뷰] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 리뷰가 하나이상 있다면 성공
			if (reviewList.size() > 0) {
				System.out.println("[MODEL_로그_상품리뷰] 성공");
				return reviewList;
			}
		}
		// selectAll 실패
		System.out.println("[MODEL_로그_리뷰]select 실패");
		return null;
	}

	public ReviewDTO selectOne(ReviewDTO rDTO) {

		// 데이터를 저장후 반환될 객체 정의
		ReviewDTO reviewDTO = null;

		// DB 연결
		conn = JDBCUtil.connect();

		// 선택한 리뷰의 상세정보를 객체에 저장하여 반환
		if (rDTO.getSearchCondition().equals("리뷰상세")) {

			System.out.println("[MODEL_로그_리뷰상세] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_REVIEW_DETAIL);
				pstmt.setInt(1, rDTO.getRID());
				System.out.println("[MODEL_로그_리뷰상세] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_리뷰상세] 쿼리문 실행");

				if (rs.next()) {

					// 리뷰의 데이터를 저장할 객체 생성
					reviewDTO = new ReviewDTO();

					// 리뷰 작성일을 분까지만 출력하기 위해 형식변환
					reviewDTO.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String createTime = timeFormat.format(reviewDTO.getCreateTime());

					// 객체에 리뷰 데이터 저장
					reviewDTO.setRID(rs.getInt("R_ID"));
					reviewDTO.setMID(rs.getString("M_ID"));
					reviewDTO.setBID(rs.getInt("B_ID"));
					reviewDTO.setScore(rs.getInt("SCORE"));
					reviewDTO.setContents(rs.getString("CONTENTS"));
					reviewDTO.setCreateTime(rs.getTimestamp("CREATE_TIME"));
					reviewDTO.setAncCreateTime(createTime);
					reviewDTO.setAncPID(rs.getInt("P_ID"));
					reviewDTO.setAncPName(rs.getString("P_NAME"));
					reviewDTO.setAncMName(rs.getString("M_NAME"));
					reviewDTO.setAncEmail(rs.getString("EMAIL"));
					reviewDTO.setImageName(rs.getString("IMAGENAME"));
				} else {
					reviewDTO = null;
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_리뷰상세] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 객체가 null이 아니라면 성공
			if (reviewDTO != null) {
				System.out.println("[MODEL_로그_리뷰상세] 성공");
				return reviewDTO;
			}

			// 해당 제품의 평균별점을 반환하는 기능
		} else if (rDTO.getSearchCondition().equals("별점평균")) {

			System.out.println("[MODEL_로그_별점평균] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_REVIEW_AVG_SCORE);
				pstmt.setInt(1, rDTO.getAncPID());
				System.out.println("[MODEL_로그_별점평균] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_별점평균] 쿼리문 실행");

				// 쿼리문 실행 결과가 있다면 실행
				if (rs.next()) {

					// 별점평균을 저장할 객체 생성
					reviewDTO = new ReviewDTO();

					// 객체에 별점평균을 저장
					reviewDTO.setAncAvgScore(rs.getDouble("AVG_SCORE"));
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_별점평균] 예외처리");
				e.printStackTrace();
				return null;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 객체가 null이 아니라면 성공
			if (reviewDTO != null) {
				System.out.println("[MODEL_로그_별점평균] 성공");
				return reviewDTO;
			}
		}
		// select 실패
		System.out.println("[MODEL_로그_리뷰]select 실패");
		return null;
	}

	public boolean insert(ReviewDTO rDTO) {

		// 쿼리문 실행 결과를 저장할 변수
		int result = 0;

		// DB연결
		conn = JDBCUtil.connect();

		// 리뷰작성에 필요한 데이터를 전달받아 DB에 추가하는 기능
		if (rDTO.getSearchCondition().equals("리뷰작성")) {

			System.out.println("[MODEL_로그_리뷰작성] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(INSERT_REVIEW);
				pstmt.setString(1, rDTO.getMID());
				pstmt.setInt(2, rDTO.getBID());
				pstmt.setInt(3, rDTO.getScore());
				pstmt.setString(4, rDTO.getContents());
				pstmt.setString(5, rDTO.getImageName());
				System.out.println("[MODEL_로그_리뷰작성] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_리뷰작성] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_리뷰작성] 예외처리");
				e.printStackTrace();
				return false;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 쿼리문 실행결과가 있다면 성공
			if (result > 0) {
				System.out.println("[MODEL_로그_리뷰작성] 성공");
				return true;
			}
		}
		// insert 실패
		System.out.println("[MODEL_로그_리뷰]insert 실패");
		return false;
	}

	private boolean update(ReviewDTO rDTO) {
		return false;
	}

	public boolean delete(ReviewDTO rDTO) {

		// 쿼리문 실행 결과를 저장할 변수
		int result = 0;

		// DB 연결
		conn = JDBCUtil.connect();

		// 선택한 리뷰를 삭제하는 기능
		if (rDTO.getSearchCondition().equals("리뷰삭제")) {

			System.out.println("[MODEL_로그_리뷰삭제] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건설정
				pstmt = conn.prepareStatement(DELETE_REVIEW);
				pstmt.setInt(1, rDTO.getRID());
				System.out.println("[MODEL_로그_리뷰삭제] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_리뷰삭제] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_리뷰삭제] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 쿼리 실행 결과가 있다면 성공
			if (result > 0) {
				System.out.println("[MODEL_로그_리뷰삭제] 성공");
				return true;
			}

		}
		// delete 실패
		System.out.println("[MODEL_로그_리뷰]delete 실패");
		return false;
	}
}

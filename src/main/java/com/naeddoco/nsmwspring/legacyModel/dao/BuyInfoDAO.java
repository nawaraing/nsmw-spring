package com.naeddoco.nsmwspring.legacyModel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyModel.dto.BuyInfoDTO;
import com.naeddoco.nsmwspring.legacyModel.util.JDBCUtil;



public class BuyInfoDAO {

	// DB의 연결 해제를 담당하는 객체
	private Connection conn;
	// SQL 쿼리 사용을 위한 객체
	private PreparedStatement pstmt;

	// 내 구매내역(해당 회원의 구매내역을 불러온다)
	// 선택할 컬럼 B_ID, M_ID, P_ID, CP_ID, 주문번호, 배송상태, 구매수량, 지불금액, 구매시간, 주소, 상품명, 상품주소
	// 테이블 BUYINFO
	// 조인 상품 테이블
	// 조건 M_ID가 전달받은 값과 같은 행
	// SearchCondition = 구매내역
	private static final String SELECTALL_BUY_INFO_LIST = "SELECT "
			+ "B.B_ID, B.M_ID, B.P_ID, B.CP_ID, B.ORDER_NUM, B.DELI_STATE, B.B_QTY, B.PAYMENT_PRICE, B.BUY_TIME, B.B_POSTCODE, B.B_ADDRESS, B.B_DETAILED_ADDRESS, HAS_REVIEW, "
			+ "P.P_NAME, P.IMAGE_PATH "
			+ "FROM BUYINFO B "
			+ "JOIN PRODUCT P ON B.P_ID = P.P_ID "
			+ "WHERE B.M_ID = ? "
			+ "ORDER BY B.ORDER_NUM DESC";

	// 판매량 반환
	// 전달받은 값과 P_ID가 일치하는 행의 B_Qty를 더한다
	// SearchCondition = 판매량
	private static final String SELECTONE_BUY_INFO__QTY = "SELECT SUM(B_Qty) AS TOTAL_QTY FROM BUYINFO WHERE P_ID = ?";

	// 주문번호 반환
	// 구매내역 테이블에서 주문번호의 최대값 +1을 반환
	private static final String SELECTONE_BUY_INFO_MAX_ORDER_NUM = "SELECT NVL(MAX(ORDER_NUM),1000)+1 AS MAX_ORDER_NUM FROM BUYINFO";

	// 구매내역 추가
	// B_ID, M_ID, P_ID, CP_ID, 주문번호, 배송상태, 구매수량, 지불금액, 구매일, 배송지
	// 주문번호는 구매내역 테이블에서 ORDER_NUM컬럼의 가장 큰값에서 1을 더해서 반환한다
	// DELI_STATE는 테이블에서 DEFAULT로 결재완료를 입력함_01_30 제거
	private static final String INSERT_BUY_INFO = "INSERT INTO BUYINFO "
			+ "(B_ID, M_ID, P_ID, CP_ID, ORDER_NUM, B_QTY, PAYMENT_PRICE, BUY_TIME, B_POSTCODE, B_ADDRESS, B_DETAILED_ADDRESS) "
			+ "VALUES (NVL((SELECT MAX(B_ID) FROM BUYINFO), 0) + 1, " + "?, " + "?, " + "?, " + "?, " + "?, " + "?, "
			+ "SYSTIMESTAMP, " + "?, " + "?, " + "?)";

	// 구매상태변경(환불, 취소)
	// 컨트롤러에서 환불, 취소를 받아 업데이트
	// 중프에서 사용 안하는 기능
	private static final String UPDATE_BUY_INFO_STATE = "UPDATE BUYINFO SET DELI_STATE = ? WHERE B_ID = ?";

	// 리뷰 작성여부
	// 트리거(INSERT_RIVIEW_TRIGGER)를 사용해 리뷰테이블에서 INSERT가 일어나면 실행
	// private static final String UPDATE_BUY_INFO_HAS_REVIEW = "UPDATE BUYINFO SET
	// HAS_REVIEW = 1 WHERE B_ID = ?";

	// 구매내역 삭제 없음
	private static final String DELETE_BUY_INFO = "";

	public ArrayList<BuyInfoDTO> selectAll(BuyInfoDTO bDTO) {

		// selectAll에 사용할 리스트 객체 정의
		ArrayList<BuyInfoDTO> buyList = null;
		// selectAll에 사용할 DTO 객체 정의
		BuyInfoDTO buyInfoDTO = null;

		// JDBCUtill의 메서드를 사용해 DB에 연결
		conn = JDBCUtil.connect();

		// 로그인한 회원의 구매내역을 반환
		if (bDTO.getSearchCondition().equals("구매내역")) {

			System.out.println("[MODEL_로그_구매내역] 구매내역 진입");

			// 구매내역에 집입하면 리스트 객체 생성
			buyList = new ArrayList<BuyInfoDTO>();

			// DB와 통신(pstmt)중 오류 발생 가능성이 있어서 try가 강제됨
			try {
				// SELECTALL_LIST를 준비상태로 변수에 저장
				pstmt = conn.prepareStatement(SELECTALL_BUY_INFO_LIST);
				// SELECTALL_LIST에 작성한 바인딩 변수를 작성
				pstmt.setString(1, bDTO.getMID());
				System.out.println("[MODEL_로그_구매내역] 쿼리 set");

				// pstmt에 준비된 쿼리를 실행하여 결과를 반환(행 단위로 저장됨)
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_구매내역] 쿼리 실행 성공");

				// 행을 확인해서 데이터가 있다면 실행하고 바디가 종료되면 다음 행으로 넘어감
				while (rs.next()) {

					buyInfoDTO = new BuyInfoDTO();

					// rs에 저장된 데이터를 JAVA에서 사용가능하게 리스트에 넣기위해 DTO객체에 값을 set하는 코드
					// timestamp는 밀리초 단위까지 출력되기 때문에 SimpleDateFormat을 사용해 형식지정(분까지)
					buyInfoDTO.setBuyTime(rs.getTimestamp("BUY_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String buyTime = timeFormat.format(buyInfoDTO.getBuyTime());

					buyInfoDTO.setBID(rs.getInt("B_ID"));
					buyInfoDTO.setMID(rs.getString("M_ID"));
					buyInfoDTO.setPID(rs.getInt("P_ID"));
					buyInfoDTO.setCPID(rs.getString("CP_ID"));
					buyInfoDTO.setOrderNum(rs.getInt("ORDER_NUM"));
					buyInfoDTO.setDeliState(rs.getString("DELI_STATE"));
					buyInfoDTO.setbQty(rs.getInt("B_QTY"));
					buyInfoDTO.setPaymentPrice(rs.getInt("PAYMENT_PRICE"));
					buyInfoDTO.setBuyTime(rs.getTimestamp("BUY_TIME"));
					buyInfoDTO.setAncBuyTime(buyTime);
					buyInfoDTO.setbPostCode(rs.getInt("B_POSTCODE"));
					buyInfoDTO.setbAddress(rs.getString("B_ADDRESS"));
					buyInfoDTO.setbDetailedAddress(rs.getString("B_DETAILED_ADDRESS"));
					buyInfoDTO.setHasReview(rs.getInt("HAS_REVIEW"));
					buyInfoDTO.setAncPName(rs.getString("P_NAME"));
					buyInfoDTO.setAncImagePath(rs.getString("IMAGE_PATH"));
					// DTO 객체에 저장된 데이터를 리스트에 추가
					buyList.add(buyInfoDTO);
				}

				// 사용이 끝난 ResultSet 객체 종료
				rs.close();

				// 오류가 발생했을 때 예외처리 로그를 출력 후 null 반환
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_구매내역] 반환 NULL_예외처리");
				e.printStackTrace();
				return null;
				// pstmt와 conn객체를 JDBCUtill의 메서드를 사용해서 반드시 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 만약 리스트의 크기가 1이상이라면 성공(buyList반환)
			if (buyList.size() > 0) {
				System.out.println("[MODEL_로그_구매내역] 구매내역 반환 성공");
				return buyList;
			}
		}
		// selectAll이 실패했을 경우 통합적으로 null을 반환
		System.out.println("[MODEL_로그_구매내역]_selectAll 실패");
		return null;
	}

	public BuyInfoDTO selectOne(BuyInfoDTO bDTO) {

		// selectOne에 사용할 DTO 객체 정의
		BuyInfoDTO buyInfoDTO = null;

		// JDBCUtil의 메서드를 사용해서 DB연결
		conn = JDBCUtil.connect();

		// 특정 상품의 PK번호를 사용해 해당 상품의 판매량을 반환
		if (bDTO.getSearchCondition().equals("판매량")) {

			System.out.println("[MODEL_로그_판매량] 판매량 진입");

			// 데이터를 저장할 DTO 객체 생성
			buyInfoDTO = new BuyInfoDTO();

			// pstmt 동작 중 오류 발생 시 예외처리
			try {
				// pstmt에 SELECTONE_BUY_INFO__QTY쿼리를 준비상대로 저장
				pstmt = conn.prepareStatement(SELECTONE_BUY_INFO__QTY);
				// 바인딩변수에 판매량을 반환받을 PID를 저장
				pstmt.setInt(1, bDTO.getPID());
				System.out.println("[MODEL_로그_판매량] 쿼리 set");

				// 쿼리문 실행 후 rs에 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_판매량] 쿼리 실행 성공");

				// rs의 첫 행이 있다면 실행
				if (rs.next()) {
					buyInfoDTO.setbQty(rs.getInt("TOTAL_QTY"));
				} else {
					buyInfoDTO = null;
				}

				// DB연결 종료
				rs.close();

			} catch (SQLException e) {
				System.out.println("[MODEL_로그_판매량] 반환 NULL_예외처리");
				e.printStackTrace();
				return null;
			} finally {
				JDBCUtil.disconnect(pstmt, conn);

				// DTO객체가 null이 아니라면 반환
				if (buyInfoDTO != null) {
					System.out.println("[MODEL_로그_판매량] 판매량 반환 성공");
					return buyInfoDTO;
				}

			}
			// 같이 구매한 상품의 주문번호를 같은 값으로 주기 위한 기능
		} else if (bDTO.getSearchCondition().equals("주문번호")) {

			System.out.println("[MODEL_로그_주문번호] 주문번호 진입");

			// 값을 저장할 DTO 객체 생성
			buyInfoDTO = new BuyInfoDTO();

			// pstmt는 예외처리가 강제됨
			try {
				// 쿼리문 준비
				pstmt = conn.prepareStatement(SELECTONE_BUY_INFO_MAX_ORDER_NUM);

				// 쿼리문 실행
				ResultSet rs = pstmt.executeQuery();

				// rs의 첫 행이 있다면 주문번호의 최대값 +1을 저장함
				if (rs.next()) {
					buyInfoDTO.setOrderNum(rs.getInt("MAX_ORDER_NUM"));
				}

				// rs 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_주문번호] 반환 NULL_예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// 주문번호는 NVL함수로 반환하기 때문에 null이 무조건 성공한다
			System.out.println("[MODEL_로그_주문번호] 주문번호 반환");
			return buyInfoDTO;

		}
		// selectOne이 실패했을 경우 통합적으로 null을 반환
		System.out.println("[MODEL_로그_구매내역]_selectOne 실패");
		return null;
	}

	public boolean insert(BuyInfoDTO bDTO) {

		// executeUpdate는 쿼리문 실행결과 영향을 받은 행의 수를 반환하기 때문에
		// 쿼리문 성공 여부를 저장하기 위한 변수
		int result = 0;

		// DB에 연결
		conn = JDBCUtil.connect();

		// 구매페이지에서 결재를 완료 했을 때 사용하는 기능
		if (bDTO.getSearchCondition().equals("구매내역추가")) {

			System.out.println("[MODEL_로그_구매내역추가] 구매내역추가 진입");

			// 예외처리 강제
			try {
				// 쿼리문 준비
				pstmt = conn.prepareStatement(INSERT_BUY_INFO);
				pstmt.setString(1, bDTO.getMID());
				pstmt.setInt(2, bDTO.getPID());
				pstmt.setString(3, bDTO.getCPID());
				pstmt.setInt(4, bDTO.getOrderNum());
				pstmt.setInt(5, bDTO.getbQty());
				pstmt.setInt(6, bDTO.getPaymentPrice());
				pstmt.setInt(7, bDTO.getbPostCode());
				pstmt.setString(8, bDTO.getbAddress());
				pstmt.setString(9, bDTO.getbDetailedAddress());
				System.out.println("[MODEL_로그_구매내역추가] 쿼리 set");

				// 쿼리문 실행
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_구매내역추가] 쿼리 실행 성공");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_구매내역추가] 예외처리 false 반환");
				e.printStackTrace();
				return false;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}

			// insert성공 시 true 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_구매내역추가] 구매내역 추가 성공");
				return true;
			}
		}
		// insert 실패
		System.out.println("[MODEL_로그_구매내역추가] 구매내역추가 실패 false 반환");
		return false;
	}

	// 중프에는 사용하지 않는 기능
//	public boolean update(BuyInfoDTO bDTO) {
//
//		// try에 오류 발생 예상코드만 작성하기 위한 scope 오류
//		int result = 0;
//
//		// DB에 연결
//		conn = JDBCUtil.connect();
//
//		// 구매상태 변경
//		if (bDTO.getSearchCondition().equals("구매상태변경")) {
//			
//			System.out.println("[MODEL_로그_구매상태변경] 구매상태변경 진입");
//
//			// 예외처리 강제
//			try {
//				// 쿼리문 준비
//				pstmt = conn.prepareStatement(UPDATE_STATE);
//				pstmt.setString(1, bDTO.getDeliState());
//				pstmt.setInt(2, bDTO.getBID());
//				System.out.println("[MODEL_로그_구매상태변경] 쿼리 set");
//
//				// 쿼리문 실행
//				result = pstmt.executeUpdate();
//				System.out.println("[MODEL_로그_구매상태변경] 쿼리 실행 성공");
//
//				// 예외처리
//			} catch (SQLException e) {
//				System.out.println("[MODEL_로그_구매상태변경] 예외처리 false 반환");
//				e.printStackTrace();
//				return false;
//				// DB연결 종료
//			} finally {
//				JDBCUtil.disconnect(pstmt, conn);
//			}
//			if (result > 0) {
//				System.out.println("[로그_구매상태변경] 구매상태변경 성공");
//				return true;
//			}
//		}
//		System.out.println("[로그_구매상태변경] 구매상태변경 실패 false 반환");
//		return false;
//	}

	public boolean delete(BuyInfoDTO bDTO) {
		return false;
	}

}

package com.naeddoco.nsmwspring.legacyModel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyModel.dto.CartDTO;
import com.naeddoco.nsmwspring.legacyModel.util.JDBCUtil;

public class CartDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	// 장바구니 목록 출력
	// 장바구니 테이블의 P_ID로 상품 테이블과 조인
	// C_ID, M_ID, P_ID, C_QTY, P_NAME, SELLING_PRICE, IMAGE_PATH
	// C_ID, M_ID는 장바구니 비우기에 사용
	// P_NAME, SELLING_PRICE, IMAGE_PATH 사용자에서 어떤 상품인지 보여줌 
	// P_ID 는 구매 후 상품 재고감소에 사용함
	private static final String SELECTALL_CART = "SELECT C.C_ID, M.M_ID, C.P_ID, C.C_QTY, P.P_NAME, P.SELLING_PRICE, P.IMAGE_PATH "
			+ "FROM CART C " + "JOIN PRODUCT P ON C.P_ID = P.P_ID " + "JOIN MEMBER M ON C.M_ID = M.M_ID "
			+ "WHERE M.M_ID = ?";

	// 장바구니 상품확인(존재여부)
	// 해당 회원의 장바구니에 해당 상품이 있는지 확인
	// 장바구니에 상품 추가 시 사용하고 결과에 따라서 사용하는 쿼리문이 달라짐
	// 어떤 회원이 어떤 상품을 몇개 담아놨는지 확인
	private static final String SELECTONE_CART = "SELECT P_ID, M_ID, C_ID, C_QTY FROM CART WHERE M_ID = ? AND P_ID = ?";

	// 장바구니 추가
	// 장바구니 상품확인 결과 NUll이면 사용됨
	private static final String INSERT_CART = "INSERT INTO CART (C_ID, M_ID, P_ID, C_QTY) VALUES (NVL((SELECT MAX(C_ID) FROM CART), 0)+1, ?, ?, ?)";

	// 장바구니 갱신(기존에 있던 상품 추가)
	// 장바구니 상품확인 결과 NUll이 아니라면 사용됨
	private static final String UPDATE_CART_QTY_ADD = "UPDATE CART SET C_QTY = C_QTY + ? WHERE C_ID = ?";

	// 장바구니 상품 1개삭제
	// 장바구니에서 해당 상품을 제외하는데 사용함
	// 장바구니 비우기(상품 구매 후 구매한 상품 삭제)
	// ctrl에서 반복문으로 사용
	private static final String DELETE_CART = "DELETE FROM CART WHERE C_ID = ?";
	

	// CRUD 중 R
	public ArrayList<CartDTO> selectAll(CartDTO cDTO) {

		// DB에서 데이터를 받아와 저장하고 반환될 객체
		ArrayList<CartDTO> cartList = null;
		CartDTO cartDTO = null;

		// JDBCUtil을 사용해 DB연결
		conn = JDBCUtil.connect();

		// M_ID가 입력값과 같은 행의 데이터를 저장 후 반환하는 코드
		if (cDTO.getSearchCondition().equals("장바구니목록출력")) {
			
			System.out.println("[MODEL_로그_장바구니목록출력] 장바구니목록출력 진입");
			
			// 장바구니 리스트를 생성
			cartList = new ArrayList<CartDTO>();

			// 예외처리 try-catch
			try {
				// Java에서 쿼리문을 사용가능하게 준비하는 코드
				pstmt = conn.prepareStatement(SELECTALL_CART);
				// 쿼리문의 바인딩변수(?)에 조건을 작성
				pstmt.setString(1, cDTO.getMID());
				
				System.out.println("[MODEL_로그_장바구니목록출력] 쿼리 set");
				
				// 쿼리문 실행 후 ResultSet객체에 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_장바구니목록출력] 쿼리 실행");

				// rs에 행이 있다면 실행 후 다음 행이 있는지 확인
				while (rs.next()) {
					
					// 장바구니에 담을 정보를 저장할 객체
					cartDTO = new CartDTO();
					cartDTO.setCID(rs.getInt("C_ID"));
					cartDTO.setMID(rs.getString("M_ID"));
					cartDTO.setPID(rs.getInt("P_ID"));
					cartDTO.setcQty(rs.getInt("C_QTY"));
					cartDTO.setAncPName(rs.getString("P_NAME"));
					cartDTO.setAncSellingPrice(rs.getInt("SELLING_PRICE"));
					cartDTO.setAncImagePath(rs.getString("IMAGE_PATH"));
					// 정보를 저장한 객체를 리스트에 추가
					cartList.add(cartDTO);
				}
				
				// ResultSet 객체를 종료
				rs.close();

				// 프로그램 동작 중 오류가 발생하면 작동할 예외코드
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_장바구니목록출력] 예외처리");
				e.printStackTrace();
				return null;
				// DB사용이 끝난 후 JDBCUtil을 사용해 연결해제
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 장바구니에 상품이 하나라도 담겼다면 장바구니를 반환
			if (cartList.size() > 0) {
				System.out.println("[MODEL_로그_장바구니목록출력] cartList 반환");
				return cartList;
			}
			
		}
		// selectAll실패 시 null반환
		System.out.println("[MODEL_로그_장바구니]selectAll 실패");
		return null;
	}

	// CRUD 중 R
	public CartDTO selectOne(CartDTO cDTO) {

		// 데이터를 저장받아 반환될 객체 정의
		CartDTO cartDTO = null;

		// DB연결
		conn = JDBCUtil.connect();

		// 해당회원의 장바구니에 해당 상품이 있는지 확인
		if (cDTO.getSearchCondition().equals("상품확인")) {
			
			System.out.println("[MODEL_로그_상품확인] 상품확인 진입");

			// 예외처리 try-catch
			try {
				// Java에서 쿼리문을 사용가능한 상태로 저장
				pstmt = conn.prepareStatement(SELECTONE_CART);
				// 쿼리문 조건 설정
				pstmt.setString(1, cDTO.getMID());
				pstmt.setInt(2, cDTO.getPID());
				System.out.println("[MODEL_로그_상품확인] 쿼리 set");

				// 쿼리문 실행 후 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_상품확인] 쿼리 실행 성공");

				// 쿼리문 실행 결과가 있다면 객체에 데이터를 저장하는 조건문
				if (rs.next()) {
					// 객체 생성
					cartDTO = new CartDTO();
					cartDTO.setMID(rs.getString("M_ID"));
					cartDTO.setPID(rs.getInt("P_ID"));
					cartDTO.setCID(rs.getInt("C_ID"));
				}
				// 쿼리문 실행 결과가 없다면 객체에 null을 저장
				else {
					cartDTO = null;
				}
				
				// 사용끝난 객체를 종료
				rs.close();

				// 프로그램 동작 중 오류가 발생하면 작동할 예외코드
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_상품확인] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 객체가 null이 아니라면 저장된 객체 반환
			if (cartDTO != null) {
				System.out.println("[MODEL_로그_상품확인] 성공");
				return cartDTO;
			}
		}
		System.out.println("[MODEL_로그_장바구니]selectAll 실패");
		return null;
	}

	//CRUD 중 C
	public boolean insert(CartDTO cDTO) {
		
		// executeUpdate는 쿼리문 실행결과 영향을 받은 행의 수를 반환하기 때문에
		// 쿼리문 성공 여부를 저장하기 위한 변수
		int result = 0;

		// DB 연결
		conn = JDBCUtil.connect();

		// 장바구니 테이블에 어떤 회원이 어떤 상품을 몇개 담았는지 저장
		if (cDTO.getSearchCondition().equals("장바구니추가")) {
			
			System.out.println("[MODEL_로그_장바구니추가] 진입");
			
			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(INSERT_CART);
				pstmt.setString(1, cDTO.getMID());
				pstmt.setInt(2, cDTO.getPID());
				pstmt.setInt(3, cDTO.getcQty());

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_장바구니추가] 쿼리 실행 성공");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_장바구니추가] 예외처리");
				e.printStackTrace();
				return false;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 성공시 true 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_장바구니추가] 성공");
				return true;
			}
			
		}
		// insert 실패 시 false 반환
		System.out.println("[MODEL_로그_장바구니]insert 실패");
		return false;
	}

	// CRUD 중 U
	public boolean update(CartDTO cDTO) {

		// 쿼리문 실행 결과를 반환하기 위한 변수
		int result = 0;
		
		// DB 연결
		conn = JDBCUtil.connect();

		// 회원의 장바구니에 상품이 있을 때 추가로 장바구니에 담기 선택시 원래 있던 장바구니를 갱신
		if (cDTO.getSearchCondition().equals("동일상품추가")) {
			
			System.out.println("[MODEL_로그_동일상품추가] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(UPDATE_CART_QTY_ADD);
				pstmt.setInt(1, cDTO.getcQty());
				pstmt.setInt(2, cDTO.getCID());

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_동일상품추가] 쿼리 실행 성공");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_동일상품추가] 예외처리");
				e.printStackTrace();
				return false;
				// DB 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 성공 시 true 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_동일상품추가] 성공");
				return true;
			}
			
		}
		// update 실패 시 false반환
		System.out.println("[MODEL_로그_장바구니]update 실패");
		return false;
	}

	//CRUD 중 D
	public boolean delete(CartDTO cDTO) {
		
		// 쿼리문 실행 결과를 반환하기 위한 변수
		int result = 0;

		// DB연결
		conn = JDBCUtil.connect();

		// 선택한 상품을 장바구니에서 제거
		if (cDTO.getSearchCondition().equals("장바구니비우기")) {
			
			System.out.println("[MODEL_로그_장바구니비우기] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(DELETE_CART);
				pstmt.setInt(1, cDTO.getCID());

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_장바구니비우기] 퀴리 실행 성공");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_장바구니비우기] 예외처리");
				e.printStackTrace();
				return false;
				// DB 연결 해제
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 성공 시 true 반환
			if (result > 0) {
				System.out.println("[MODEL_로그_장바구니비우기] 성공");
				return true;
			}

		}
		// delete 실패 시 false 반환
		System.out.println("[MODEL_로그_장바구니]delete 실패");
		return false;
	}
}

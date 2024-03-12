package com.naeddoco.nsmwspring.model.cartModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("CartDAO")
public class CartDAO {
	
	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//  SQL
	// 장바구니 목록 출력
	// 장바구니 테이블의 P_ID로 상품 테이블과 조인
	// C_ID, M_ID, P_ID, C_QTY, P_NAME, SELLING_PRICE, IMAGE_PATH
	// C_ID, M_ID는 장바구니 비우기에 사용
	// P_NAME, SELLING_PRICE, IMAGE_PATH 사용자에서 어떤 상품인지 보여줌 
	// P_ID 는 구매 후 상품 재고감소에 사용함
//	private static final String SELECTALL_CART = "SELECT C.C_ID, M.M_ID, C.P_ID, C.C_QTY, P.P_NAME, P.SELLING_PRICE, P.IMAGE_PATH "
//			+ "FROM CART C " + "JOIN PRODUCT P ON C.P_ID = P.P_ID " + "JOIN MEMBER M ON C.M_ID = M.M_ID "
//			+ "WHERE M.M_ID = ?";
	private static final String SELECTALL_CART = "";

	// 장바구니 상품확인(존재여부)
	// 해당 회원의 장바구니에 해당 상품이 있는지 확인
	// 장바구니에 상품 추가 시 사용하고 결과에 따라서 사용하는 쿼리문이 달라짐
	// 어떤 회원이 어떤 상품을 몇개 담아놨는지 확인
//	private static final String SELECTONE_CART = "SELECT P_ID, M_ID, C_ID, C_QTY FROM CART WHERE M_ID = ? AND P_ID = ?";
	private static final String SELECTONE_CART = "";
	
	// 장바구니 추가
	// 장바구니 상품확인 결과 NUll이면 사용됨
//	private static final String INSERT_CART = "INSERT INTO CART (C_ID, M_ID, P_ID, C_QTY) VALUES (NVL((SELECT MAX(C_ID) FROM CART), 0)+1, ?, ?, ?)";
	private static final String INSERT_CART = "";
	
	// 장바구니 갱신(기존에 있던 상품 추가)
	// 장바구니 상품확인 결과 NUll이 아니라면 사용됨
//	private static final String UPDATE_CART_QTY_ADD = "UPDATE CART SET C_QTY = C_QTY + ? WHERE C_ID = ?";
	
	private static final String UPDATE_CART_QTY_ADD = "";
	// 장바구니 상품 1개삭제
	// 장바구니에서 해당 상품을 제외하는데 사용함
	// 장바구니 비우기(상품 구매 후 구매한 상품 삭제)
	// ctrl에서 반복문으로 사용
//	private static final String DELETE_CART = "DELETE FROM CART WHERE C_ID = ?";
	private static final String DELETE_CART = "";
	

	// CRUD 중 R
	public List<CartDTO> selectAll(CartDTO cDTO) {
		return (List<CartDTO>)jdbcTemplate.query(SELECTALL_CART, new CartRowMapper());
	}

	// CRUD 중 R
	public CartDTO selectOne(CartDTO cDTO) {

		Object[] args = { cDTO.getMemberID(), cDTO.getProductID() };
		try {
			return jdbcTemplate.queryForObject(SELECTONE_CART, args, new CartRowMapper());
		} catch (Exception e) {
			System.out.println("[MODEL_로그_장바구니]selectOne 예외처리");
			return null;
		}
	}

	//CRUD 중 C
	public boolean insert(CartDTO cDTO) {
	
		int result = jdbcTemplate.update(INSERT_CART,
											cDTO.getMemberID(),
											cDTO.getProductID(),
											cDTO.getProductQuantity());
		if(result <= 0) {
			System.out.println("[MODEL_로그_장바구니]insert 실패");
			return false;
		}
		System.out.println("[MODEL_로그_장바구니]insert 성공");
		return true;
		
		
	}

	// CRUD 중 U
	public boolean update(CartDTO cDTO) {


		int result = jdbcTemplate.update(UPDATE_CART_QTY_ADD,
											cDTO.getProductQuantity(),
											cDTO.getCartID());
		if(result <= 0) {
			System.out.println("[MODEL_로그_장바구니]update 실패");
			return false;
		}
		System.out.println("[MODEL_로그_동일상품추가] 성공");
		return true;
	
	}

	//CRUD 중 D
	public boolean delete(CartDTO cDTO) {
		

		int result = jdbcTemplate.update(DELETE_CART,
										cDTO.getCartID());
		if(result <= 0) {
			System.out.println("[MODEL_로그_장바구니]delete 실패");
			return false;
		}
		System.out.println("[MODEL_로그_장바구니비우기] 성공");
		return true;
	}	
	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
class CartRowMapper implements RowMapper<CartDTO> {

	@Override
	public CartDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CartDTO data = new CartDTO();
		data.setCartID(rs.getInt("CART_ID"));
		data.setMemberID(rs.getString("MEMBER_ID"));
		data.setProductID(rs.getInt("PRODUCT_ID"));
		data.setProductQuantity(rs.getInt("PRODUCT_QUANTITY"));
			
		return data;
	}
	
}


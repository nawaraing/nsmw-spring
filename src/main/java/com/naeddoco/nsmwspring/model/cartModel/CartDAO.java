package com.naeddoco.nsmwspring.model.cartModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoDAO;

import lombok.extern.slf4j.Slf4j;

@Repository("cartDAO")
@Slf4j
public class CartDAO {
	
	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//장바구니 목록 출력(해당 회원)
	//판매상태 'SALES'인 상품만 조회 
	private static final String SELECTALL_CART = "SELECT C.CART_ID, C.MEMBER_ID, C.PRODUCT_ID, C.PRODUCT_QUANTITY, P.PRODUCT_NAME, P.SALE_PRICE, I.IMAGE_PATH, CA.CATEGORY_NAME " +
												 "FROM CART C " +
												 "INNER JOIN PRODUCT P ON C.PRODUCT_ID = P.PRODUCT_ID " +
												 "INNER JOIN PRODUCT_IMAGE PI ON P.PRODUCT_ID = PI.PRODUCT_ID " +
												 "INNER JOIN IMAGE I ON PI.IMAGE_ID = I.IMAGE_ID " +
												 "INNER JOIN PRODUCT_CATEGORY PC ON PC.PRODUCT_ID = P.PRODUCT_ID " +
												 "INNER JOIN CATEGORY CA ON CA.CATEGORY_ID = PC.CATEGORY_ID " +
												 "WHERE P.SALE_STATE = 'SALES' AND C.MEMBER_ID = ?";
												
	//장바구니에 해당 상품 추가시 장바구니에 중복 상품이 있는지 확인
	//결과가 반환되면 중복상품 존재 -> update
	//없다면 존재하지 않음 -> insert
	private static final String SELECTONE_CART = "SELECT CART_ID, MEMBER_ID, PRODUCT_ID, PRODUCT_QUANTITY "
												+ "FROM CART WHERE MEMBER_ID = ? "
												+ "AND PRODUCT_ID = ?";
	
	//장바구니 신규 추가
	private static final String INSERT_CART = "INSERT INTO CART (MEMBER_ID, PRODUCT_ID, PRODUCT_QUANTITY) VALUES (?, ?, ?)";
	
	//장바구니 중복상품 수량 갱신
	private static final String UPDATE_CART_QTY_ADD = "UPDATE CART SET PRODUCT_QUANTITY = PRODUCT_QUANTITY + ? WHERE CART_ID = ?";
	
	// 장바구니 상품을 삭제하는 쿼리
	private static final String DELETE_CART = "DELETE FROM CART WHERE CART_ID = ?";
	
/*------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public List<CartDTO> selectAll(CartDTO cartDTO) {
		
		log.debug("selectAll 진입");

		//회원아이디로 장바구니 목록 조회
		if (cartDTO.getSearchCondition().equals("selectCartDatas")) {
			
			log.debug("selectCartDatas 진입");
			
			Object[] args = { cartDTO.getMemberID() };
			
			try {
			
				return jdbcTemplate.query(SELECTALL_CART, args, new SelectCartDatasRowMapper());
			
			} catch (Exception e) {
				
				log.debug("selectCartDatas 예외 발생");
				
				return null;
			}
			
		}
		
		log.debug("selectAll 실패");
		
		return null;
		
	}

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public CartDTO selectOne(CartDTO cartDTO) {
		
		log.debug("selectOne 진입");

		//장바구니 중복상품 확인
		if (cartDTO.getSearchCondition().equals("checkCartProductData")) {
			
			log.debug("checkCartProductData 진입");
			
			Object[] args = { cartDTO.getMemberID(), cartDTO.getProductID() };

			try {
				
				return jdbcTemplate.queryForObject(SELECTONE_CART, args, new CheckCartRowMapper());
				
			} catch (Exception e) {
				
				log.debug("checkCartProductData 예외 발생");
				
				return null;
				
			}
			
		}
		
		log.debug("selectOne 실패");
		
		return null;
		
	}

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	public boolean insert(CartDTO cartDTO) {
		
		log.debug("insert 진입");
		
		//장바구니 신규 추가
		if (cartDTO.getSearchCondition().equals("insertProductData")) {
			
			log.debug("insertProductData 진입");
			
			int result = jdbcTemplate.update(INSERT_CART, cartDTO.getMemberID(), cartDTO.getProductID(), cartDTO.getProductQuantity());
			
			if(result <= 0) {
				
				log.debug("insertProductData 실패");
				
				return false;
				
			}
			
			log.debug("insertProductData 성공");
			
			return true;
			
		}
		
		log.debug("insert 실패");
		
		return false;
		
	}

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public boolean update(CartDTO cartDTO) {
		
		log.debug("update 진입");
		
		//장바구니 중복상품 수량 추가
		int result = jdbcTemplate.update(UPDATE_CART_QTY_ADD, cartDTO.getProductQuantity(), cartDTO.getCartID());
		
		if(result <= 0) {
			
			log.debug("updateProductData 실패");
			
			return false;
			
		}
		
		log.debug("updateProductData 성공");
		
		return true;
		
	}

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public boolean delete(CartDTO cartDTO) {
		
		log.debug("delete 진입");
		
		if(cartDTO.getSearchCondition().equals("deleteCart")) {
			
			log.debug("deleteCart 진입");
			
			int result = 0;
			
			try {
			
				result = jdbcTemplate.update(DELETE_CART,cartDTO.getCartID());
			
			} catch (Exception e) {
				
				log.debug("deleteCart 예외 발생");
				
				return false;
				
			}
			
			if(result <= 0) {
				
				log.debug("deleteCart 실패");
				
				return false;
				
			}
			
			log.debug("deleteCart 성공");
			
			return true;
			
		}
		
		log.debug("delete 실패");
		
		return false;
			
	}	
	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
class SelectCartDatasRowMapper implements RowMapper<CartDTO> {

	@Override
	public CartDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CartDTO cartDTO = new CartDTO();
		
		cartDTO.setCartID(rs.getInt("C.CART_ID"));
		cartDTO.setMemberID(rs.getString("C.MEMBER_ID"));
		cartDTO.setProductID(rs.getInt("C.PRODUCT_ID"));
		cartDTO.setProductQuantity(rs.getInt("C.PRODUCT_QUANTITY"));
		cartDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		cartDTO.setAncSalePrice(rs.getInt("P.SALE_PRICE"));
		cartDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		cartDTO.setAncCategory(rs.getString("CA.CATEGORY_NAME"));

		return cartDTO;
		
	}
	
}

class CheckCartRowMapper implements RowMapper<CartDTO> {

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


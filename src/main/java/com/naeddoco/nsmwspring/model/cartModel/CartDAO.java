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

	private static final String SELECTALL_CART = "";

	private static final String SELECTONE_CART = "";
	
	//장바구니 추가
	private static final String INSERT_CART = "INSERT INTO CART (MEMBER_ID, PRODUCT_ID, PRODUCT_QUANTITY) VALUES (?, ?, ?);";
	
	private static final String UPDATE_CART = "";
	
	private static final String DELETE_CART = "";
	
	
	public List<CartDTO> selectAll(CartDTO cartDTO) {
		log.debug("selectAll start");
		return (List<CartDTO>)jdbcTemplate.query(SELECTALL_CART, new CartRowMapper());
	}

	
	public CartDTO selectOne(CartDTO cartDTO) {

//		Object[] args = { cartDTO.getMemberID(), cartDTO.getProductID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE_CART, args, new CartRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(CartDTO cartDTO) {
		log.debug("insert 진입");
		
		//장바구니 추가
		if (cartDTO.getSearchCondition().equals("insertProductData")) {
			log.debug("insert_cart 진입");
			int result = jdbcTemplate.update(INSERT_CART,
											cartDTO.getMemberID(),
											cartDTO.getProductID(),
											cartDTO.getProductQuantity());
			if(result <= 0) {
				log.debug("insert_cart 실패");
				return false;
			}
			log.debug("insert_cart 성공");
			return true;
		}
		log.debug("insert 실패");
		return false;
	}

	
	public boolean update(CartDTO cartDTO) {


//		int result = jdbcTemplate.update(UPDATE_CART,
//											cartDTO.getProductQuantity(),
//											cartDTO.getCartID());
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	public boolean delete(CartDTO cartDTO) {
		
//		int result = jdbcTemplate.update(DELETE_CART,
//											cartDTO.getCartID());
//		if(result <= 0) {
//			log.debug("delete 성공");
			return false;
//		}
//		log.debug("delete 성공");
//		return true;
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


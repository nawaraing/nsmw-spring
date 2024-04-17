package com.naeddoco.nsmwspring.model.orderInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("orderInfoDAO")
@Slf4j
public class OrderInfoDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECTALL_GET_BEST_EIGHT = "SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.PRODUCT_DETAIL, p.SALE_PRICE, c.CATEGORY_NAME, i.IMAGE_PATH, " +
			                                               "(SELECT SUM(BUY_QUANTITY) FROM ORDER_INFO o WHERE o.PRODUCT_ID = p.PRODUCT_ID) AS cnt " +
			                                               "FROM PRODUCT p " +
			                                               "JOIN PRODUCT_CATEGORY pc ON p.PRODUCT_ID = pc.PRODUCT_ID " +
			                                               "JOIN CATEGORY c ON pc.CATEGORY_ID = c.CATEGORY_ID " +
			                                               "JOIN PRODUCT_IMAGE pi ON p.PRODUCT_ID = pi.PRODUCT_ID " +
			                                               "JOIN IMAGE i ON pi.IMAGE_ID = i.IMAGE_ID " +
			                                               "ORDER BY cnt DESC " +
			                                               "LIMIT 8";
	
	// 최근에 추가된 상품 정보를 조회하는 쿼리
	private static final String SELECTALL_RECENT_ADDED_PRODUCT = "SELECT P.PRODUCT_ID, P.PRODUCT_NAME, P.PRODUCT_DETAIL, P.SALE_PRICE, C.CATEGORY_NAME, I.IMAGE_PATH " +
			                                                     "FROM PRODUCT P " +
			                                                     "JOIN PRODUCT_CATEGORY PC ON P.PRODUCT_ID = PC.PRODUCT_ID " +
			                                                     "JOIN CATEGORY C ON PC.CATEGORY_ID = C.CATEGORY_ID " +
			                                                     "JOIN PRODUCT_IMAGE PI ON P.PRODUCT_ID = PI.PRODUCT_ID " +
			                                                     "JOIN IMAGE I ON PI.IMAGE_ID = I.IMAGE_ID " +
			                                                     "WHERE P.SALE_STATE = 'SALES' " +
			                                                     "ORDER BY REGISTER_DATE DESC " +
			                                                     "LIMIT 8";
	
	// 사용자가 산 상품과 동일한 상품이 포함되어 있는 구매내역을 랜덤으로 가져오는 쿼리
	private static final String SELECT_RANDOM_MEMBER_IDS = "SELECT DISTINCT B.MEMBER_ID " +
                                                           "FROM ORDER_INFO O " +
                                                           "INNER JOIN BUY_INFO B ON O.BUY_INFO_ID = B.BUY_INFO_ID " +
                                                           "WHERE PRODUCT_ID = ? AND B.MEMBER_ID <> ? " +
                                                           "ORDER BY RAND() " +
                                                           "LIMIT 5";
	
	// 구매 상세 내역을 추가하는 쿼리
	private static final String INSERT_ORDER_INFO = "INSERT INTO ORDER_INFO (" +
													"BUY_INFO_ID, " +
													"PRODUCT_ID, " +
													"BUY_QUANTITY, " +
													"PAYMENT_PRICE, " +
													"HAS_REVIEW" +
													") VALUES (?, ?, ?, ?, 0)";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public List<OrderInfoDTO> selectAll(OrderInfoDTO orderInfoDTO) {

		log.debug("selectAll 진입");

		if (orderInfoDTO.getSearchCondition().equals("getBuyInfoIDByProductID")) {
			
			log.debug("getBuyInfoIDByProductID 진입");
			
			Object args[] = { orderInfoDTO.getProductID(), orderInfoDTO.getAncMemberID() };
			
			try {

				return jdbcTemplate.query(SELECT_RANDOM_MEMBER_IDS, args, new GetBuyInfoIDByProductIDRowMapper());
			
			} catch (Exception e) {
				
				log.error("getBuyInfoIDByProductID 예외 발생");
				
				return null;
				
			}

		} else if(orderInfoDTO.getSearchCondition().equals("getBestEight")) {
			
			log.debug("getBestEight 진입");
			
			try {
				
				return (List<OrderInfoDTO>) jdbcTemplate.query(SELECTALL_GET_BEST_EIGHT, new GetBestEightRowMapper());

			}catch (Exception e) {
				
				log.error("getBestEight 예외 발생");
				
				return null;
				
			}
			
		} else if(orderInfoDTO.getSearchCondition().equals("getRecentAddedProduct")) {
			
			log.debug("getRecentAddedProduct 진입");
			
			try {
				
				return (List<OrderInfoDTO>) jdbcTemplate.query(SELECTALL_RECENT_ADDED_PRODUCT, new GetRencetAddedProductRowMapper());

			}catch (Exception e) {
				
				log.error("getRecentAddedProduct 예외 발생");
				
				return null;
				
			}
			
		}
		
		log.error("selectAll 실패");
		
		return null;

	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
	public boolean insert(OrderInfoDTO orderInfoDTO) {

		log.debug("insert 진입");

		int result = 0;

		if (orderInfoDTO.getSearchCondition().equals("selectSubscriptionDatas")) {
			
			log.debug("selectSubscriptionDatas 진입");

			try {
			
				result = jdbcTemplate.update(INSERT_ORDER_INFO, 
											 orderInfoDTO.getBuyInfoID(),
											 orderInfoDTO.getProductID(),
											 orderInfoDTO.getBuyQuantity(),
											 orderInfoDTO.getPaymentPrice());

			} catch (Exception e) {
			
				log.error("selectSubscriptionDatas 예외 발생");

				return false;

			}
			
			if (result <= 0) {
				
				log.error("selectSubscriptionDatas 실패");

				return false;

			}
			
			log.debug("selectSubscriptionDatas 성공");

			return true;
			
		}
		
		log.error("insert 성공");
		
		return false;

	}

}

@Slf4j
class GetBuyInfoIDByProductIDRowMapper implements RowMapper<OrderInfoDTO> {

	@Override
	public OrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("GetBuyInfoIDByProductIDRowMapper 진입");
		
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		
		orderInfoDTO.setAncMemberID(rs.getString("B.MEMBER_ID"));
		
		log.debug("GetBuyInfoIDByProductIDRowMapper 완료");

		return orderInfoDTO;

	}

}

@Slf4j
class GetBestEightRowMapper implements RowMapper<OrderInfoDTO> {

	@Override
	public OrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("GetBestEightRowMapper 처리 진입");
		
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		
		orderInfoDTO.setProductID(rs.getInt("P.PRODUCT_ID"));
		orderInfoDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		orderInfoDTO.setAncProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		orderInfoDTO.setAncSalePrice(rs.getString("P.SALE_PRICE"));
		orderInfoDTO.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
		orderInfoDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		
		log.debug("GetBestEightRowMapper 완료");

		return orderInfoDTO;

	}
}

@Slf4j
class GetRencetAddedProductRowMapper implements RowMapper<OrderInfoDTO> {

	@Override
	public OrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("GetRencetAddedProductRowMapper 처리 진입");
		
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		
		orderInfoDTO.setProductID(rs.getInt("P.PRODUCT_ID"));
		orderInfoDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		orderInfoDTO.setAncProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		orderInfoDTO.setAncSalePrice(rs.getString("P.SALE_PRICE"));
		orderInfoDTO.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
		orderInfoDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		
		log.debug("GetRencetAddedProductRowMapper 완료");

		return orderInfoDTO;

	}
}
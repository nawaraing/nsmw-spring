package com.naeddoco.nsmwspring.model.orderInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("orderInfoDAO")
@Slf4j
public class OrderInfoDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 잘 팔리는 8개 제품 습득 쿼리
	private static final String SELECTALL_GET_BEST_EIGHT = "SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.PRODUCT_DETAIL, p.SALE_PRICE, c.CATEGORY_NAME, i.IMAGE_PATH, " +
														   "(SELECT SUM(BUY_QUANTITY) FROM ORDER_INFO o WHERE o.PRODUCT_ID = p.PRODUCT_ID) AS cnt " +
														   "FROM PRODUCT p " +
														   "JOIN PRODUCT_CATEGORY pc ON p.PRODUCT_ID = pc.PRODUCT_ID " +
														   "JOIN CATEGORY c ON pc.CATEGORY_ID = c.CATEGORY_ID " +
														   "JOIN PRODUCT_IMAGE pi ON p.PRODUCT_ID = pi.PRODUCT_ID " +
														   "JOIN IMAGE i ON pi.IMAGE_ID = i.IMAGE_ID " +
														   "ORDER BY cnt DESC " +
														   "LIMIT 8";
	
	// 구매 상세 내역을 추가하는 쿼리
	private static final String INSERT_ORDER_INFO = "INSERT INTO ORDER_INFO (" +
													"BUY_INFO_ID, " +
													"PRODUCT_ID, " +
													"BUY_QUANTITY, " +
													"PAYMENT_PRICE, " +
													"HAS_REVIEW" +
													") VALUES (?, ?, ?, ?, 0)";

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<OrderInfoDTO> selectAll(OrderInfoDTO orderDTO) {
		
		log.debug("[로그] orderInfo SELECTALL 처리 진입");
		
		if(orderDTO.getSearchCondition().equals("getBestEight")) {
			
			log.debug("[로그] orderInfo getBestEight 처리 진입");
			
			try {
				
				return (List<OrderInfoDTO>) jdbcTemplate.query(SELECTALL_GET_BEST_EIGHT, new getBestEightRowMapper());

			}catch (Exception e) {
				
				log.debug("[로그] orderInfo getBestEight 예외 발생");
				
				return null;
				
			}
			
		}
		
		log.debug("[로그] orderInfo SELECTALL 처리 실패");
		
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
			
				log.debug("selectSubscriptionDatas 예외 발생");

				return false;

			}
			
		}

		if (result <= 0) {

			return false;

		}
		
		log.debug("insert 처리 실패");

		return true;

	}

}

@Slf4j
class getBestEightRowMapper implements RowMapper<OrderInfoDTO> {

	@Override
	public OrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("[로그] orderInfo getBestEightRowMapper 처리 진입");
		
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		
		orderInfoDTO.setProductID(rs.getInt("p.PRODUCT_ID"));
		orderInfoDTO.setAncProductName(rs.getString("p.PRODUCT_NAME"));
		orderInfoDTO.setAncProductDetail(rs.getString("p.PRODUCT_DETAIL"));
		orderInfoDTO.setAncSalePrice(rs.getString("p.SALE_PRICE"));
		orderInfoDTO.setAncCategoryName(rs.getString("c.CATEGORY_NAME"));
		orderInfoDTO.setAncImagePath(rs.getString("i.IMAGE_PATH"));
		
		log.debug("[로그] orderInfo getBestEightRowMapper 처리 완료");

		return orderInfoDTO;

	}

}

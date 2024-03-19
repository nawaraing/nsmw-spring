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

	private static final String SELECTALL_TOTAL_QUANTITY_DESC_LIMIT_EIGHT = "SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.PRODUCT_DETAIL, p.SALE_PRICE, c.CATEGORY_NAME, i.IMAGE_PATH, "
																		  + "(SELECT SUM(BUY_QUANTITY) FROM ORDER_INFO o WHERE o.PRODUCT_ID = p.PRODUCT_ID) AS cnt "
																		  + "FROM PRODUCT p "
																		  + "JOIN PRODUCT_CATEGORY pc ON p.PRODUCT_ID = pc.PRODUCT_ID "
																		  + "JOIN CATEGORY c ON pc.CATEGORY_ID = c.CATEGORY_ID "
																		  + "JOIN PRODUCT_IMAGE pi ON p.PRODUCT_ID = pi.PRODUCT_ID "
																		  + "JOIN IMAGE i ON pi.IMAGE_ID = i.IMAGE_ID "
																		  + "ORDER BY cnt DESC "
																		  + "LIMIT 8";

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<OrderInfoDTO> selectAll(OrderInfoDTO orderDTO) {
		
		log.debug("[로그] orderInfo SELECTALL 처리 진입");
		
		if(orderDTO.getSearchCondition().equals("getUpperEight")) {
			
			try {
				
				return (List<OrderInfoDTO>) jdbcTemplate.query(SELECTALL_TOTAL_QUANTITY_DESC_LIMIT_EIGHT, new selectAllRowMapper1());

			}catch (Exception e) {
				
				log.debug("[로그] orderInfo SELECTALL 예외 발생");
				
				return null;
				
			}
			
		}
		
		return null;

	}

}

@Slf4j
class selectAllRowMapper1 implements RowMapper<OrderInfoDTO> {

	@Override
	public OrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("[로그] orderInfo selectAllRowMapper1 처리 진입");
		
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		
		orderInfoDTO.setProductID(rs.getInt("p.PRODUCT_ID"));
		orderInfoDTO.setAncProductName(rs.getString("p.PRODUCT_NAME"));
		orderInfoDTO.setAncProductDetail(rs.getString("p.PRODUCT_DETAIL"));
		orderInfoDTO.setAncSalePrice(rs.getString("p.SALE_PRICE"));
		orderInfoDTO.setAncCategoryName(rs.getString("c.CATEGORY_NAME"));
		orderInfoDTO.setAncImagePath(rs.getString("i.IMAGE_PATH"));
		
		log.debug("[로그] orderInfo selectAllRowMapper1 처리 완료");

		return orderInfoDTO;

	}

}

package com.naeddoco.nsmwspring.model.orderInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("orderInfoDAO")
public class OrderInfoDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL_TOTAL_QUANTITY_DESC_LIMIT_EIGHT = "SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.PRODUCT_DETAIL, c.CATEGORY_NAME, i.IMAGE_PATH, "
																		  + "(SELECT SUM(BUY_QUANTITY) FROM ORDER_INFO o WHERE o.PRODUCT_ID = p.PRODUCT_ID) AS "
																		  + "FROM PRODUCT p "
																		  + "JOIN PRODUCT_CATEGORY pc ON p.PRODUCT_ID = pc.PRODUCT_ID "
																		  + "JOIN CATEGORY c ON pc.CATEGORY_ID = c.CATEGORY_ID "
																		  + "JOIN PRODUCT_IMAGE pi ON p.PRODUCT_ID = pi.PRODUCT_ID "
																		  + "JOIN IMAGE i ON pi.IMAGE_ID = i.IMAGE_ID "
																		  + "ORDER BY cnt DESC "
																		  + "LIMIT 8";

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<OrderInfoDTO> selectAll(OrderInfoDTO orderInfoDTO) {
		
		System.out.println("[로그] orderInfo selectAll 처리 진입");
		
		if(orderInfoDTO.getSearchCondition().equals("getUpperEight")) {
			
			try {
				
				return (List<OrderInfoDTO>) jdbcTemplate.query(SELECTALL_TOTAL_QUANTITY_DESC_LIMIT_EIGHT, new selectAllRowMapper1());

			} catch (Exception e) {
				
				System.out.println("[로그] orderInfo SELECTALL 예외 발생");

				return null;

			}
			
		}
		
		return null;

	}

}

class selectAllRowMapper1 implements RowMapper<OrderInfoDTO> {

	@Override
	public OrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		
		orderInfoDTO.setProductID(rs.getInt("PRODUCT_ID"));

		return orderInfoDTO;

	}

}

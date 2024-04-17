package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("buyInfoDAO")
@Slf4j
public class BuyInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 사용자의 구매 내역을 5개 가져오는 쿼리
	private static final String SELECTALL_BUY_INFO_BY_MEMBER = "SELECT DISTINCT " +
															   "O.PRODUCT_ID FROM BUY_INFO B " +
															   "INNER JOIN ORDER_INFO O ON B.BUY_INFO_ID = O.BUY_INFO_ID " +
															   "WHERE B.MEMBER_ID = ? " +
															   "LIMIT 5";
	
	// 사용자가 구매하지 않은 다른 사용자가 구매한 상품을 가져오는 쿼리
	private static final String SELCTALL_NOT_BUY_PRODUCT = "SELECT DISTINCT P.PRODUCT_ID, P.PRODUCT_NAME, P.PRODUCT_DETAIL, P.SALE_PRICE, C.CATEGORY_NAME, I.IMAGE_PATH " +
            										       "FROM BUY_INFO B " +
            										       "INNER JOIN ORDER_INFO O ON B.BUY_INFO_ID = O.BUY_INFO_ID " +
            										       "INNER JOIN PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID " +
            										       "INNER JOIN PRODUCT_CATEGORY PC ON P.PRODUCT_ID = PC.PRODUCT_ID " +
            										       "INNER JOIN CATEGORY C ON PC.CATEGORY_ID = C.CATEGORY_ID " +
            										       "INNER JOIN PRODUCT_IMAGE PI ON P.PRODUCT_ID = PI.PRODUCT_ID " +
            										       "INNER JOIN IMAGE I ON PI.IMAGE_ID = I.IMAGE_ID " +
            										       "WHERE B.MEMBER_ID = ? AND P.SALE_STATE = 'SALES' ";
	
	// 가장 높은 PK값을 가져오는 쿼리
	private static final String SELECTONE_MAX_PK = "SELECT MAX(BUY_INFO_ID) AS MAX_PK FROM BUY_INFO";

	// 구매내역을 추가하는 쿼리
	private static final String INSERT_BUY_INFO = "INSERT INTO BUY_INFO (" +
												  "MEMBER_ID, " +
												  "SUBSCRIPTION_INFO_ID, " +
												  "BUY_DATE, " +
												  "DELIVERY_POSTCODE, " +
												  "DELIVERY_ADDRESS, " +
												  "DELIVERY_DETAIL_ADDRESS, " +
												  "ORDER_STATE" +
												  ") VALUES (?, ?, current_date(), ?, ?, ?,'PAY')";

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<BuyInfoDTO> selectAll(BuyInfoDTO buyInfoDTO) {

		log.trace("selectAll 진입");

		if (buyInfoDTO.getSearchCondition().equals("getProductIDs")) {
			
			log.trace("getProductIDs 진입");
			
			Object args[] = { buyInfoDTO.getMemberID() };
			
			try {

				return jdbcTemplate.query(SELECTALL_BUY_INFO_BY_MEMBER, args, new getProductIDsRowMapper());
			
			} catch (Exception e) {
				
				log.error("getProductIDs 예외 발생");
				
				return null;
				
			}

		} else if(buyInfoDTO.getSearchCondition().equals("getNotBuyProduct")) {
			
			log.trace("getNotBuyProduct 진입");
			
			Object args[] = { buyInfoDTO.getMemberID() };
			
			try {

				return jdbcTemplate.query(SELCTALL_NOT_BUY_PRODUCT, args, new GetNotBuyProductRowMapper());
			
			} catch (Exception e) {
				
				log.error("getProductIDs 예외 발생");
				
				return null;
				
			}
			
			
		}
		
		log.error("selectAll 실패");
		
		return null;

	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public BuyInfoDTO selectOne(BuyInfoDTO buyInfoDTO) {
		
		log.trace("selectOne 진입");

		if(buyInfoDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.trace("insertSubscriptionData 진입");
		
			try {
				
				return jdbcTemplate.queryForObject(SELECTONE_MAX_PK, new selectMaxPKRowMapper());
				
			} catch (Exception e) {
				
				log.error("insertSubscriptionData 예외처리");
				
				return null;
				
			}
			
		}
		
		log.error("selectOne 실패");
		
		return null;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(BuyInfoDTO buyInfoDTO) {

		log.trace("insert 진입");

		int result = 0;

		if (buyInfoDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.trace("insertSubscriptionData 진입");

			try {
			
				result = jdbcTemplate.update(INSERT_BUY_INFO, 
										     buyInfoDTO.getMemberID(),
										     buyInfoDTO.getSubscriptionInfoID(),
										     buyInfoDTO.getDeliveryPostcode(),
										     buyInfoDTO.getDeliveryAddress(),
										     buyInfoDTO.getDeliveryDetailAddress());

			} catch (Exception e) {
			
				log.error("insertSubscriptionData 예외 발생");

				return false;

			}
			
			if (result <= 0) {
				
				log.error("insertSubscriptionData 실패");

				return false;

			}
			
			log.trace("insertSubscriptionData 성공");

			return true;
			
		}
		
		log.error("insert 실패");
		
		return false;

	}

}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class getProductIDsRowMapper implements RowMapper<BuyInfoDTO> {

	@Override
	public BuyInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.trace("getProductIDsRowMapper 진입");
		
		BuyInfoDTO buyInfoDTO = new BuyInfoDTO();
		
		buyInfoDTO.setAncProductID(rs.getInt("O.PRODUCT_ID"));
		
		log.trace("getProductIDsRowMapper 완료");

		return buyInfoDTO;

	}

}

@Slf4j
class selectMaxPKRowMapper implements RowMapper<BuyInfoDTO> {

	@Override
	public BuyInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.trace("selectMaxPKRowMapper 진입");
		
		BuyInfoDTO buyInfoDTO = new BuyInfoDTO();
		
		buyInfoDTO.setMaxPk(rs.getInt("MAX_PK"));

		log.trace("selectMaxPKRowMapper 완료");
		
		return buyInfoDTO;

	}

}

@Slf4j
class GetNotBuyProductRowMapper implements RowMapper<BuyInfoDTO> {

	@Override
	public BuyInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.trace("GetNotBuyProductRowMapper 진입");
		
		BuyInfoDTO buyInfoDTO = new BuyInfoDTO();
		
		buyInfoDTO.setAncProductID(rs.getInt("P.PRODUCT_ID"));
		buyInfoDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		buyInfoDTO.setAncProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		buyInfoDTO.setAncSalePrice(rs.getInt("P.SALE_PRICE"));
		buyInfoDTO.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
		buyInfoDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));

		log.trace("GetNotBuyProductRowMapper 완료");
		
		return buyInfoDTO;

	}

}
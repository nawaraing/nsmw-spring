package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("buyInfoDAO")
@Slf4j
public class BuyInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL_GET_NOT_BUY_PRODUCT = "";
	
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

		log.debug("[로그] buyInfo SELECTALL 처리 진입");

		if (buyInfoDTO.getSearchCondition().equals("getNotBuyProduct")) {
			
			log.debug("[로그] buyInfo getNotBuyProduct 처리 진입");
			
			try {

				return (List<BuyInfoDTO>) jdbcTemplate.query(SELECTALL_GET_NOT_BUY_PRODUCT, new getNotBuyProductRowMapper());
			
			} catch (Exception e) {
				
				log.debug("[로그] buyInfo getNotBuyProduct 예외 발생");
				
				return null;
				
			}

		}
		
		log.debug("[로그] buyInfo getNotBuyProduct 처리 실패");
		
		return null;

	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public BuyInfoDTO selectOne(BuyInfoDTO buyInfoDTO) {
		
		log.debug("selectOne 진입");

		if(buyInfoDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.debug("insertSubscriptionData 진입");
		
			try {
				
				return jdbcTemplate.queryForObject(SELECTONE_MAX_PK, new selectMaxPKRowMapper());
				
			} catch (Exception e) {
				
				log.debug("insertSubscriptionData 예외처리");
				
				return null;
				
			}
			
		}
		
		log.debug("selectOne 실패");
		
		return null;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(BuyInfoDTO buyInfoDTO) {

		log.debug("insert 진입");

		int result = 0;

		if (buyInfoDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.debug("insertSubscriptionData 진입");

			try {
			
				result = jdbcTemplate.update(INSERT_BUY_INFO, 
										     buyInfoDTO.getMemberID(),
										     buyInfoDTO.getSubscriptionInfoID(),
										     buyInfoDTO.getDeliveryPostcode(),
										     buyInfoDTO.getDeliveryAddress(),
										     buyInfoDTO.getDeliveryDetailAddress());

			} catch (Exception e) {
			
				log.debug("insertSubscriptionData 예외 발생");

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

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class getNotBuyProductRowMapper implements RowMapper<BuyInfoDTO> {

	@Override
	public BuyInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuyInfoDTO data = new BuyInfoDTO();

		// rs에 저장된 데이터를 JAVA에서 사용가능하게 리스트에 넣기위해 DTO객체에 값을 set하는 코드
		// timestamp는 밀리초 단위까지 출력되기 때문에 SimpleDateFormat을 사용해 형식지정(분까지)
		// data.setBuyDate(rs.getTimestamp("BUY_DATE"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// String buyTime = timeFormat.format(data.getBuyDate());

		data.setBuyInfoID(rs.getInt("BUY_INFO_ID"));
		data.setMemberID(rs.getString("MEMBER_ID"));
		data.setBuyDate(rs.getTimestamp("BUY_DATE"));
		data.setDeliveryPostcode(rs.getInt("DELIVERY_POSTCODE"));
		data.setDeliveryAddress(rs.getString("DELIVERY_ADDRESS"));
		data.setDeliveryDetailAddress(rs.getString("DELIVERY_DETAIL_ADDRESS"));
		data.setOrderState(rs.getString("ORDER_STATE"));

		log.debug(rs.getString("BUY_INFO_ID"));
		log.debug(rs.getString("MEMBER_ID"));
		log.debug(sdf.format(rs.getTimestamp("BUY_DATE")));
		log.debug(Integer.toString(rs.getInt("DELIVERY_POSTCODE")));
		log.debug(rs.getString("DELIVERY_ADDRESS"));
		log.debug(rs.getString("DELIVERY_DETAIL_ADDRESS"));
		log.debug(rs.getString("ORDER_STATE"));

		return data;

	}

}

@Slf4j
class selectMaxPKRowMapper implements RowMapper<BuyInfoDTO> {

	@Override
	public BuyInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("selectMaxPKRowMapper 진입");
		
		BuyInfoDTO buyInfoDTO = new BuyInfoDTO();
		
		buyInfoDTO.setMaxPk(rs.getInt("MAX_PK"));

		log.debug("selectMaxPKRowMapper 완료");
		
		return buyInfoDTO;

	}

}
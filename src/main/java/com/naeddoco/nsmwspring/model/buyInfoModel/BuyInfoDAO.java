package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
	
	private static final String SELECTALL = "SELECT BUYINFO_ID, MEMBER_ID, BUY_DATE, DELIVERY_POSTCODE, "
											+ "DELIVERY_ADDRESS, DELIVERY_DETAIL_ADDRESS, ORDER_STATE FROM BUYINFO";

	private static final String SELECTONE = "";

	private static final String INSERT = "";
		
	private static final String UPDATE = "";

	private static final String DELETE = "";

		
	public List<BuyInfoDTO> selectAll(BuyInfoDTO buyInfoDTO) {
		log.debug("selectAll start");
		return (List<BuyInfoDTO>)jdbcTemplate.query(SELECTALL, new BuyInfoRowMapper());
	}

	public BuyInfoDTO selectOne(BuyInfoDTO buyInfoDTO) {
			
//		Object[] args = { buyInfoDTO.getBuyInfoID(), buyInfoDTO.getMemberID(), buyInfoDTO.getBuyDate(), buyInfoDTO.getDeliveryPostcode(), buyInfoDTO.getDeliveryAddress(), buyInfoDTO.getDeliveryDetailAddress(), buyInfoDTO.getOrderState() };
//		log.debug("selectOne start");
//		
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new BuyInfoRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}
			

	public boolean insert(BuyInfoDTO buyInfoDTO) {

//		int result = jdbcTemplate.update(INSERT,
//										buyInfoDTO.getBuyInfoID(),
//										buyInfoDTO.getMemberID(),
//										buyInfoDTO.getBuyDate(),
//										buyInfoDTO.getDeliveryPostcode(),
//										buyInfoDTO.getDeliveryAddress(),
//										buyInfoDTO.getDeliveryDetailAddress(),
//										buyInfoDTO.getOrderState());
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(BuyInfoDTO buyInfoDTO) {
//
//		int result = jdbcTemplate.update(UPDATE,
//										buyInfoDTO.getBuyInfoID(),
//										buyInfoDTO.getMemberID(),
//										buyInfoDTO.getBuyDate(),
//										buyInfoDTO.getDeliveryPostcode(),
//										buyInfoDTO.getDeliveryAddress(),
//										buyInfoDTO.getDeliveryDetailAddress(),
//										buyInfoDTO.getOrderState());
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(BuyInfoDTO buyInfoDTO) {
//
//		int result = jdbcTemplate.update(DELETE,
//										buyInfoDTO.getBuyInfoID(),
//										buyInfoDTO.getMemberID(),
//										buyInfoDTO.getBuyDate(),
//										buyInfoDTO.getDeliveryPostcode(),
//										buyInfoDTO.getDeliveryAddress(),
//										buyInfoDTO.getDeliveryDetailAddress(),
//										buyInfoDTO.getOrderState());
//		if(result <= 0) {
//			log.debug("delete 성공");
			return false;
//		}
//		log.debug("delete 성공");
//		return true;
	}
}

@Slf4j
class BuyInfoRowMapper implements RowMapper<BuyInfoDTO> {

	@Override
	public BuyInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuyInfoDTO data = new BuyInfoDTO();

		// rs에 저장된 데이터를 JAVA에서 사용가능하게 리스트에 넣기위해 DTO객체에 값을 set하는 코드
		// timestamp는 밀리초 단위까지 출력되기 때문에 SimpleDateFormat을 사용해 형식지정(분까지)
		//data.setBuyDate(rs.getTimestamp("BUY_DATE"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//String buyTime = timeFormat.format(data.getBuyDate());

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

















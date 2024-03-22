package com.naeddoco.nsmwspring.model.shippingAddressModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("shippingAddressDAO")
@Slf4j
public class ShippingAddressDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";

	//회원가입시 기본 배송지 추가 기능
	private static final String INSERT = "INSERT INTO SHIPPING_ADDRESS "
									+ "(MEMBER_ID, SHIPPING_DEFAULT, SHIPPING_ADDRESS_NAME, SHIPPING_POSTCODE, SHIPPING_ADDRESS, SHIPPING_DETAIL_ADDRESS) "
									+ "VALUES(?, 1, '', ?, ?, ?)";

	private static final String UPDATE = "";

	private static final String DELETE = "";

	public List<ShippingAddressDTO> selectAll(ShippingAddressDTO shippingAddressDTO) {

//		return (List<ShippingAddressDTO>) jdbcTemplate.query(SELECTALL, new ShippingAddressRowMapper());
		return null;

	}

	public ShippingAddressDTO selectOne(ShippingAddressDTO shippingAddressDTO) {

		return null;

	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(ShippingAddressDTO shippingAddressDTO) {

		log.trace("insert 진입");

		int result = 0;

		if (shippingAddressDTO.getSearchCondition().equals("joinShippingAddress")) {
			log.trace("joinShippingAddress 진입");

			result = jdbcTemplate.update(INSERT, shippingAddressDTO.getMemberID(), 
												shippingAddressDTO.getShippingPostcode(),
												shippingAddressDTO.getShippingAddress(), 
												shippingAddressDTO.getShippingDetailAddress());
		}

		if (result <= 0) {
			log.trace("joinShippingAddress 실패");

			return false;

		}

		log.trace("insert 성공");

		return true;

	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	// 현재 미지원 기능
	public boolean update(ShippingAddressDTO shippingAddressDTO) {

		return false;

	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(ShippingAddressDTO shippingAddressDTO) {

		return false;

	}

}

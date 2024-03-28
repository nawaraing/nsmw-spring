package com.naeddoco.nsmwspring.model.productImageModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("productImageDAO")
@Slf4j
public class ProductImageDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO PRODUCT_IMAGE (IMAGE_ID, PRODUCT_ID) VALUES (?, ?)";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
		
	public boolean insert(ProductImageDTO productImageDTO) {
		
		log.debug("insert 처리 진입");
	
		int result = jdbcTemplate.update(INSERT, productImageDTO.getImageID(), productImageDTO.getProductID());
		
		if(result <= 0) {
			
			log.debug("insert 실패");
			
			return false;
			
		}
		
		log.debug("insert 성공");
		
		return true;
		
	}
		
}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class ImageRowMapper implements RowMapper<ProductImageDTO> {
	@Override
	public ProductImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductImageDTO data = new ProductImageDTO();
		
		data.setImageID(rs.getInt("IMAGE_ID"));
			
		log.debug(Integer.toString(rs.getInt("IMAGE_ID")));
		log.debug(rs.getString("IMAGE_PATH"));
		
		return data;
	}
	
}

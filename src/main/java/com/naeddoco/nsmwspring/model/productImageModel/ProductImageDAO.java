package com.naeddoco.nsmwspring.model.productImageModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("productImageDAO")
@Slf4j
public class ProductImageDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = "INSERT INTO PRODUCT_IMAGE (IMAGE_ID, PRODUCT_ID) VALUES (?, ?)";
	
	private static final String DELETE = "DELETE FROM PRODUCT_IMAGE WHERE IMAGE_ID = ?";
	
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
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean delete(ProductImageDTO productImageDTO) {
		
		int result = 0;
		
		if(productImageDTO.getSearchCondition().equals("deleteAdminProductImageDatas")) {
			
			try {
				
				result = jdbcTemplate.update(DELETE, productImageDTO.getImageID());
				
				if(result <= 0) {
					
					log.debug("deleteAdminProductImageDatas 실패");
			
					return false;
				
				}
			
			} catch (Exception e) {
				
				log.debug("deleteAdminProductImageDatas 예외 발생");
				
				return false;
				
			}
			
		}	
			
		log.debug("delete 성공");
			
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

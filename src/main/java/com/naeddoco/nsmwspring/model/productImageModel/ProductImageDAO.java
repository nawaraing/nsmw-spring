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

	private static final String SELECTALL = "SELECT IMAGE_ID FROM PRODUCT_IMAGE WHERE PRODUCT_ID = ?";
	
	// 새로운 상품-이미지 데이터를 추가하는 쿼리
	private static final String INSERT = "INSERT INTO PRODUCT_IMAGE (IMAGE_ID, PRODUCT_ID, PRIORITY) VALUES (?, ?, ?)";
	
	// 상품-이미지 데이터를 삭제하는 쿼리
	private static final String DELETE = "DELETE FROM PRODUCT_IMAGE WHERE IMAGE_ID = ?";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public List<ProductImageDTO> selectAll(ProductImageDTO productImageDTO) {
		
		log.debug("selectAll 진입");
		
		if(productImageDTO.getSearchCondition().equals("getImageID")) {
			
			log.debug("getImageID 진입");
			
			Object[] args = { productImageDTO.getProductID() };
			
			try {
				
				return jdbcTemplate.query(SELECTALL, args, new GetImageIDRowMapper());

			}catch (Exception e) {
				
				log.error("getImageID 예외 발생");
				
				return null;
				
			}
			
		}
		
		log.error("selectAll 실패");
		
		return null;

	}
		
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
		
	public boolean insert(ProductImageDTO productImageDTO) {
		
		log.debug("insert 진입");
		
		if(productImageDTO.getSearchCondition().equals("insertProductByAdmin")) {
			
			log.debug("insertProductByAdmin 진입");
			
			int result = jdbcTemplate.update(INSERT, productImageDTO.getImageID(), productImageDTO.getProductID(), productImageDTO.getPriority());
			
			if(result <= 0) {
				
				log.debug("insertProductByAdmin 실패");
				
				return false;
				
			}
			
			log.debug("insertProductByAdmin 성공");
			
			return true;
			
		}
		
		log.debug("insert 실패");
		
		return false;
		
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

@Slf4j
class GetImageIDRowMapper implements RowMapper<ProductImageDTO> {
	
	@Override
	public ProductImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProductImageDTO data = new ProductImageDTO();
		
		data.setImageID(rs.getInt("IMAGE_ID"));
			
		return data;
		
	}
	
}
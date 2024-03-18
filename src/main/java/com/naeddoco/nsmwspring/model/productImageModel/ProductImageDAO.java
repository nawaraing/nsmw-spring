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

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "INSERT INTO PRODUCT_IMAGE (IMAGE_ID, PRODUCT_ID) VALUES (?, ?)";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
	
	public List<ProductImageDTO> selectAll(ProductImageDTO productImageDTO) {
		
		log.debug("[로그] productImage selectAll 처리 진입");
		
		return (List<ProductImageDTO>)jdbcTemplate.query(SELECTALL, new ImageRowMapper());
		
	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public ProductImageDTO selectOne(ProductImageDTO productImageDTO) {

			return null;
			
	}	
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean insert(ProductImageDTO productImageDTO) {
		
		log.debug("[로그] productImage insert 처리 진입");
	
		int result = jdbcTemplate.update(INSERT, productImageDTO.getImageID(), productImageDTO.getProductID());
		
		if(result <= 0) {
			
			log.debug("[로그] productImage insert 실패");
			
			return false;
			
		}
		
		log.debug("[로그] productImage insert 성공");
		
		return true;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean update(ProductImageDTO productImageDTO) {

			return false;
			
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean delete(ProductImageDTO productImageDTO) {
		
			return false;

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

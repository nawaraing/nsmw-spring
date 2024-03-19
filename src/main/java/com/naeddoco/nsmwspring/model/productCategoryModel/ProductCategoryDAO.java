package com.naeddoco.nsmwspring.model.productCategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

@Repository("productCategoryDAO")
public class ProductCategoryDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";

	private static final String INSERT = "INSERT INTO PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_ID) VALUES (?, ?)";

	private static final String UPDATE = "";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public List<ProductCategoryDTO> selectAll(ProductCategoryDTO productCategoryDTO) {
		
		System.out.println("[로그] productCategory selectAll 처리 진입");
		
		return (List<ProductCategoryDTO>) jdbcTemplate.query(SELECTALL, new productCategoryRowMapper());
		
	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public ProductCategoryDTO selectOne(ProductCategoryDTO productCategoryDTO) {
		
		System.out.println("[로그] productCategory selectOne 처리 진입");
		
		Object[] args = { productCategoryDTO.getProductCategoryID()};
		
		try {
			
			return jdbcTemplate.queryForObject(SELECTONE, args, new productCategoryRowMapper());
			
		} catch (Exception e) {
			
			return null;
			
		}
		
	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	// PK제외 모든 항목
	public boolean insert(ProductCategoryDTO productCategoryDTO) {
		
		System.out.println("[로그] productCategory insert 처리 진입");
		
		int result = jdbcTemplate.update(INSERT, productCategoryDTO.getProductID(), productCategoryDTO.getCategoryID());
	
		if (result <= 0) {
			
			System.out.println("[로그] productCategory insert 처리 실패");
			
			return false;
			
		}
		
		System.out.println("[로그] productCategory insert 처리 성공");
		
		return true;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean update(ProductCategoryDTO productCategoryDTO) {
		
		System.out.println("[로그] productCategory update 처리 진입");
		
		int result = jdbcTemplate.update(UPDATE, productCategoryDTO.getProductID(), productCategoryDTO.getCategoryID());
		
		if (result <= 0) {
			
			return false;
			
		}
		
		return true;
		
	}
		
}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
class productCategoryRowMapper implements RowMapper<ProductCategoryDTO> {
	
	@Override
	public ProductCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		System.out.println("[로그] productCategoryRowMapper 처리 진입");
		
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
		
		productCategoryDTO.setProductCategoryID(rs.getInt("PRODUCT_CATEGORY_ID"));
		productCategoryDTO.setProductID(rs.getInt("PRODUCT_ID"));
		productCategoryDTO.setCategoryID(rs.getInt("CATEGORY_ID"));
		
		return productCategoryDTO;
		
	}
	
}
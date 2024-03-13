package com.naeddoco.nsmwspring.model.productModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("productDAO")
public class ProductDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 쿼리문
	private static final String SELECTALL = "";
	
	private static final String SELECTONE = "";

	private static final String INSERT = "INSERT INTO PRODUCT "
			+ "(PRODUCT_NAME, PRODUCT_DETAIL, COST_PRICE, RETAIL_PRICE, SALE_PRICE, STOCK, INGREDIENT, USAGE, EXPIRATION_DATE, REGISTER_DATE, MODIFY_DATE, SALE_STATE) "
			+ "	VALUES "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "CURRENT_TIMESTAMP, "
			+ "CURRENT_TIMESTAMP, "
			+ "CURRENT_TIMESTAMP, "
			+ "? "
			+ "	)";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
/*------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public List<ProductDTO> selectAll(ProductDTO productDTO) {
		
		return (List<ProductDTO>) jdbcTemplate.query(SELECTALL, new productRowMapper());
		
	}

	public ProductDTO selectOne(ProductDTO productDTO) {
		
		Object[] args = { productDTO.getProductID()};
		
		try {
			
			return jdbcTemplate.queryForObject(SELECTONE, args, new productRowMapper());
			
		} catch (Exception e) {
			
			return null;
			
		}
		
	}

	// PK제외 모든 항목
	public boolean insert(ProductDTO productDTO) {
		
		int result = jdbcTemplate.update(INSERT, productDTO.getProductName(), productDTO.getProductDetail(), productDTO.getCostPrice(), productDTO.getRetailPrice(), productDTO.getSalePrice(), productDTO.getStock(),productDTO.getIngredient(), productDTO.getUsage(), productDTO.getSaleState());
		
		if (result <= 0) {
			
			return false;
			
		}
		
		return true;
		
	}

	// 
	public boolean update(ProductDTO productDTO) {
		int result = jdbcTemplate.update(UPDATE, 
										productDTO.getProductName(), 
										productDTO.getProductDetail(), 
										productDTO.getCostPrice(), 
										productDTO.getRetailPrice(),
										productDTO.getSalePrice(),
										productDTO.getStock(),
										productDTO.getIngredient(),
										productDTO.getUsage(),
										productDTO.getExpirationDate(),
										productDTO.getRegisterDate(),
										productDTO.getModifyDate(),
										productDTO.getSaleState()
				                         );
		if (result <= 0) {
			return false;
		}
		return true;
	}

	// 상품 영구 삭제기능 미지원
	public boolean delete(ProductDTO productDTO) {
		int result = jdbcTemplate.update(DELETE				                         
                                         );
		if (result <= 0) {
			return false;
		}
		return true;
	}
}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
class productRowMapper implements RowMapper<ProductDTO> {
	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductDTO data = new ProductDTO();
		data.setProductID(rs.getInt("PRODUCT_ID"));
		data.setProductName(rs.getString("PRODUCT_PASSWORD"));
		data.setProductDetail(rs.getString("PRODUCT__DETAIL"));
		data.setCostPrice(rs.getInt("COST_PRICE"));
		data.setRetailPrice(rs.getInt("RETAIL_PRICE"));
		data.setSalePrice(rs.getInt("SALE_PRICE"));
		data.setStock(rs.getInt("STOCK"));
		data.setIngredient(rs.getString("INGREDIENT"));
		data.setUsage(rs.getString("USAGE"));
		data.setExpirationDate(rs.getString("EXPIRATION_DATE"));
		data.setRegisterDate(rs.getString("REGISTER_DATE"));
		data.setModifyDate(rs.getString("MODIFY_DATE"));
		data.setSaleState(rs.getString("SALE_DATE"));
		return data;
	}
}
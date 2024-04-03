package com.naeddoco.nsmwspring.model.productCategoryModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.couponCategoryModel.CouponCategoryDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("productCategoryDAO")
@Slf4j
public class ProductCategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String INSERT = "INSERT INTO PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_ID) VALUES (?, ?)";
	
	private static final String DELETE = "DELETE FROM PRODUCT_CATEOGRY WHERE PRODUCT_ID = ?";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	// PK제외 모든 항목
	public boolean insert(ProductCategoryDTO productCategoryDTO) {
		
		log.debug("insert 진입");
		
		if(productCategoryDTO.getSearchCondition().equals("insertProductByAdmin")) {
			
			log.debug("insertProductByAdmin 진입");
			
			int result = jdbcTemplate.update(INSERT, productCategoryDTO.getProductID(), productCategoryDTO.getCategoryID());
			
			if (result <= 0) {
				
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

	public boolean delete(ProductCategoryDTO productCategoryDTO) {

		log.debug("delete 진입");
		
		if (productCategoryDTO.getSearchCondition().equals("updateAdminProductListData")) {

			log.debug("updateAdminProductListData 진입");
			
			int result = jdbcTemplate.update(DELETE, productCategoryDTO.getProductID());

			if(result <= 0) {
				
				log.debug("updateAdminProductListData 실패");
				
				return false;
			}
			
			log.debug("updateAdminProductListData 성공");
			
			return true;
		}
		
		log.error("delete 실패");
		
		return false;
	}
	
}
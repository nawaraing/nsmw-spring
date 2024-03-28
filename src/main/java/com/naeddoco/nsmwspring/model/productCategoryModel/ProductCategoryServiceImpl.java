package com.naeddoco.nsmwspring.model.productCategoryModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Autowired
	private ProductCategoryDAO productCategoryDAO;

	@Override
	public boolean insert(ProductCategoryDTO productCategoryDTO) {
		
		productCategoryDAO.insert(productCategoryDTO); // 성공
		
		return productCategoryDAO.insert(productCategoryDTO); // 실패
		
	}

	@Override
	public boolean delete(ProductCategoryDTO productCategoryDTO) {
		
		return productCategoryDAO.delete(productCategoryDTO);
		
	}
	
}
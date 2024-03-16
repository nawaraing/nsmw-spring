package com.naeddoco.nsmwspring.model.productCategoryModel;

import java.util.List;

public interface ProductCategoryService {
	
	public List<ProductCategoryDTO> selectAll(ProductCategoryDTO productCategoryDTO);
	
	public ProductCategoryDTO selectOne(ProductCategoryDTO productCategoryDTO);
	
	public boolean insert(ProductCategoryDTO productCategoryDTO);
	
	public boolean update(ProductCategoryDTO productCategoryDTO);
	
}

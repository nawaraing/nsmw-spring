package com.naeddoco.nsmwspring.model.categoryModel;

import java.util.List;

public interface CategoryService {
	
	public List<CategoryDTO> selectAll(CategoryDTO categoryDTO);
	public CategoryDTO selectOne(CategoryDTO categoryDTO);
	
	
}

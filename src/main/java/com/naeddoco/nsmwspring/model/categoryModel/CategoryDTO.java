package com.naeddoco.nsmwspring.model.categoryModel;

import lombok.Data;

@Data
public class CategoryDTO {
	private int categoryID; // 인조 식별자
	private String categoryName; // 이름
	
	@Override
	public String toString() {
		
		return "CategoryDTO [" + "categoryID = " + categoryID + ", " 
							   + "categoryName = " + categoryName
				               + "]";
				
	}

}

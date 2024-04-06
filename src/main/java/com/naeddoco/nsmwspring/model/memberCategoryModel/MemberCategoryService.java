package com.naeddoco.nsmwspring.model.memberCategoryModel;

import java.util.List;

public interface MemberCategoryService {
	public List<MemberCategoryDTO> selectAll(MemberCategoryDTO memberCategoryDTO);
	public boolean insert(MemberCategoryDTO memberCategoryDTO);
}

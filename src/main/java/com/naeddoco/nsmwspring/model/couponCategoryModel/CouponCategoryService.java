package com.naeddoco.nsmwspring.model.couponCategoryModel;

import java.util.List;

public interface CouponCategoryService {
	public List<CouponCategoryDTO> selectAll(CouponCategoryDTO couponCategoryDTO);
	public CouponCategoryDTO selectOne(CouponCategoryDTO couponCategoryDTO);
	public boolean insert(CouponCategoryDTO couponCategoryDTO);
	public boolean update(CouponCategoryDTO couponCategoryDTO);
	public boolean delete(CouponCategoryDTO couponCategoryDTO);
}

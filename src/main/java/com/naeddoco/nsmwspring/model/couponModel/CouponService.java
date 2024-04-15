package com.naeddoco.nsmwspring.model.couponModel;

import java.util.List;


public interface CouponService {
	
	public List<CouponDTO> selectAll(CouponDTO couponDTO);
	public CouponDTO selectOne(CouponDTO couponDTO);
	public boolean insert(CouponDTO couponDTO);
	public boolean update(CouponDTO couponDTO);
	public boolean delete(CouponDTO couponDTO);
}

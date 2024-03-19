package com.naeddoco.nsmwspring.model.wonCoupon;

import java.util.List;


public interface WonCouponService {
	public List<WonCouponDTO> selectAll(WonCouponDTO wonCouponDTO);
	public WonCouponDTO selectOne(WonCouponDTO wonCouponDTO);
	public boolean insert(WonCouponDTO wonCouponDTO);
	public boolean update(WonCouponDTO wonCouponDTO);
	public boolean delete(WonCouponDTO wonCouponDTO);
}

package com.naeddoco.nsmwspring.model.wonCoupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wonCouponService")
public class WonCouponServiceImpl implements WonCouponService {
	
	@Autowired
	private WonCouponDAO wonCouponDAO;
	
	@Override
	public boolean insert(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.insert(wonCouponDTO);
	}

	@Override
	public boolean update(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.update(wonCouponDTO);
	}

}

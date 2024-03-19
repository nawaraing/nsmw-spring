package com.naeddoco.nsmwspring.model.wonCoupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wonCouponService")
public class WonCouponServiceImpl implements WonCouponService {
	
	@Autowired
	private WonCouponDAO wonCouponDAO;
	
	@Override
	public List<WonCouponDTO> selectAll(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.selectAll(wonCouponDTO);
	}

	@Override
	public WonCouponDTO selectOne(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.selectOne(wonCouponDTO);
	}

	@Override
	public boolean insert(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.insert(wonCouponDTO);
	}

	@Override
	public boolean update(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.update(wonCouponDTO);
	}

	@Override
	public boolean delete(WonCouponDTO wonCouponDTO) {
		return wonCouponDAO.delete(wonCouponDTO);
	}

}

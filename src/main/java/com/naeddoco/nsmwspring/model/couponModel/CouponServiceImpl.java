package com.naeddoco.nsmwspring.model.couponModel;

import java.util.List;

import org.springframework.stereotype.Service;


@Service("couponService")
public class CouponServiceImpl implements CouponService{

	private CouponDAO couponDAO;

	@Override
	public List<CouponDTO> selectAll(CouponDTO couponDTO) {
		return couponDAO.selectAll(couponDTO);
	}

	@Override
	public CouponDTO selectOne(CouponDTO couponDTO) {
		return couponDAO.selectOne(couponDTO);
	}

	@Override
	public boolean insert(CouponDTO couponDTO) {
		return couponDAO.insert(couponDTO);
	}

	@Override
	public boolean update(CouponDTO couponDTO) {
		return couponDAO.update(couponDTO);
	}

	@Override
	public boolean delete(CouponDTO couponDTO) {
		return couponDAO.delete(couponDTO);
	}
	

}

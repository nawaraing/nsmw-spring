package com.naeddoco.nsmwspring.model.percentageCouponModel;

import java.util.List;

public interface PercentageCouponService {
	public List<PercentageCouponDTO> selectAll(PercentageCouponDTO percentageCouponDTO);
	public PercentageCouponDTO selectOne(PercentageCouponDTO percentageCouponDTO);
	public boolean insert(PercentageCouponDTO percentageCouponDTO);
	public boolean update(PercentageCouponDTO percentageCouponDTO);
	public boolean delete(PercentageCouponDTO percentageCouponDTO);
}

package com.naeddoco.nsmwspring.model.provisionGradeCouponModel;

import java.util.List;


public interface ProvisionGradeCouponService {
	public List<ProvisionGradeCouponDTO> selectAll(ProvisionGradeCouponDTO provisionGradeCouponDTO);
	public ProvisionGradeCouponDTO selectOne(ProvisionGradeCouponDTO provisionGradeCouponDTO);
	public boolean insert(ProvisionGradeCouponDTO provisionGradeCouponDTO);
	public boolean update(ProvisionGradeCouponDTO provisionGradeCouponDTO);
	public boolean delete(ProvisionGradeCouponDTO provisionGradeCouponDTO);
}

package com.naeddoco.nsmwspring.model.provisionBatchCouponModel;

import java.util.List;

public interface ProvisionBatchCouponService {
	public List<ProvisionBatchCouponDTO> selectAll(ProvisionBatchCouponDTO provisionBatchCouponDTO);
	public ProvisionBatchCouponDTO selectOne(ProvisionBatchCouponDTO provisionBatchCouponDTO);
	public boolean insert(ProvisionBatchCouponDTO provisionBatchCouponDTO);
	public boolean update(ProvisionBatchCouponDTO provisionBatchCouponDTO);

}

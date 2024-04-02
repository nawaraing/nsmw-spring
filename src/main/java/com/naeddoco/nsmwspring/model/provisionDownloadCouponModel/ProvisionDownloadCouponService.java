package com.naeddoco.nsmwspring.model.provisionDownloadCouponModel;

import java.util.List;

public interface ProvisionDownloadCouponService {
	public List<ProvisionDownloadCouponDTO> selectAll(ProvisionDownloadCouponDTO provisionDownloadCouponDTO);
	public ProvisionDownloadCouponDTO selectOne(ProvisionDownloadCouponDTO provisionDownloadCouponDTO);
	public boolean insert(ProvisionDownloadCouponDTO provisionDownloadCouponDTO);
	public boolean update(ProvisionDownloadCouponDTO provisionDownloadCouponDTO);
	public boolean delete(ProvisionDownloadCouponDTO provisionDownloadCouponDTO);
}

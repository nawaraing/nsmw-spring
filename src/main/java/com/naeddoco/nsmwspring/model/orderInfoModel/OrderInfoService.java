package com.naeddoco.nsmwspring.model.orderInfoModel;

import java.util.List;

public interface OrderInfoService {

	public List<OrderInfoDTO> selectAll(OrderInfoDTO orderInfoDTO);
	public boolean insert(OrderInfoDTO orderInfoDTO);

}

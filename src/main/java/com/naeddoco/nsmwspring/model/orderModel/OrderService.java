package com.naeddoco.nsmwspring.model.orderModel;

import java.util.List;

public interface OrderService {

	public List<OrderDTO> selectAll(OrderDTO orderDTO);

}

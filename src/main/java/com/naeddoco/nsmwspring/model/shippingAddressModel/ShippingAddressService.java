package com.naeddoco.nsmwspring.model.shippingAddressModel;

import java.util.List;

public interface ShippingAddressService {
	public List<ShippingAddressDTO> selectAll(ShippingAddressDTO shippingAddressDTO);
	public ShippingAddressDTO selectOne(ShippingAddressDTO shippingAddressDTO);
	public boolean insert(ShippingAddressDTO shippingAddressDTO);
	public boolean update(ShippingAddressDTO shippingAddressDTO);
	public boolean delete(ShippingAddressDTO shippingAddressDTO);

}

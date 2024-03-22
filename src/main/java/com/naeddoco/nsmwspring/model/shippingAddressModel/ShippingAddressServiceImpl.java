package com.naeddoco.nsmwspring.model.shippingAddressModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("shippingAddressService")
public class ShippingAddressServiceImpl  implements ShippingAddressService{

	@Autowired
	private ShippingAddressDAO shippingAddressDAO;
	
	@Override
	public List<ShippingAddressDTO> selectAll(ShippingAddressDTO shippingAddressDTO) {
		return shippingAddressDAO.selectAll(shippingAddressDTO);
	}

	@Override
	public ShippingAddressDTO selectOne(ShippingAddressDTO shippingAddressDTO) {
		return shippingAddressDAO.selectOne(shippingAddressDTO);
	}

	@Override
	public boolean insert(ShippingAddressDTO shippingAddressDTO) {
		return shippingAddressDAO.insert(shippingAddressDTO);
	}

	@Override
	public boolean update(ShippingAddressDTO shippingAddressDTO) {
		return shippingAddressDAO.update(shippingAddressDTO);
	}

	@Override
	public boolean delete(ShippingAddressDTO shippingAddressDTO) {
		return shippingAddressDAO.delete(shippingAddressDTO);
	}

}

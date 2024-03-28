package com.naeddoco.nsmwspring.model.productImageModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productImageService")
public class ProductImageServiceImpl implements ProductImageService {
	
	@Autowired
	private ProductImageDAO productImageDAO;
	
	@Override
	public boolean insert(ProductImageDTO productImageDTO) {
		productImageDAO.insert(productImageDTO); // 성공
		return productImageDAO.insert(productImageDTO); // 실패
	}

}

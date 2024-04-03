package com.naeddoco.nsmwspring.model.productImageModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;

@Service("productImageService")
public class ProductImageServiceImpl implements ProductImageService {
	
	@Autowired
	private ProductImageDAO productImageDAO;
	
	@Override
	public boolean insert(ProductImageDTO productImageDTO) {
		
		return productImageDAO.insert(productImageDTO);
		
	}
	
	@Override
	public boolean delete(ProductImageDTO productImageDTO) {
		
		return productImageDAO.delete(productImageDTO);
		
	}

}

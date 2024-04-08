package com.naeddoco.nsmwspring.model.productImageModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productImageService")
public class ProductImageServiceImpl implements ProductImageService {
	
	@Autowired
	private ProductImageDAO productImageDAO;
	
	@Override
	public List<ProductImageDTO> selectAll(ProductImageDTO productImageDTO){
		
		return productImageDAO.selectAll(productImageDTO);
		
	}
	
	@Override
	public boolean insert(ProductImageDTO productImageDTO) {
		
		return productImageDAO.insert(productImageDTO);
		
	}
	
	@Override
	public boolean delete(ProductImageDTO productImageDTO) {
		
		return productImageDAO.delete(productImageDTO);
		
	}

}

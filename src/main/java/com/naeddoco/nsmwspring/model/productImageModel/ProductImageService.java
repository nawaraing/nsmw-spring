package com.naeddoco.nsmwspring.model.productImageModel;

import java.util.List;

public interface ProductImageService {
	
	public List<ProductImageDTO> selectAll(ProductImageDTO productImageDTO);
	
	public boolean insert(ProductImageDTO productImageDTO);
	
	public boolean delete(ProductImageDTO productImageDTO);

}

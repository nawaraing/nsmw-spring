package com.naeddoco.nsmwspring.model.productModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public List<ProductDTO> selectAll(ProductDTO productDTO) {
		return productDAO.selectAll(productDTO);
	}

	@Override
	public ProductDTO selectOne(ProductDTO productDTO) {
		return productDAO.selectOne(productDTO);
	}

	@Override
	public boolean insert(ProductDTO productDTO) {
		return productDAO.insert(productDTO); 
	}

	@Override
	public boolean update(ProductDTO productDTO) {
		return productDAO.update(productDTO);
	}

}

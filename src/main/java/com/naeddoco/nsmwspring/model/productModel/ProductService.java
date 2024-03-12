package com.naeddoco.nsmwspring.model.productModel;

import java.util.List;

public interface ProductService {
	public List<ProductDTO> selectAll(ProductDTO mDTO);
	public ProductDTO selectOne(ProductDTO mDTO);
	public boolean insert(ProductDTO mDTO);
	public boolean update(ProductDTO mDTO);
	public boolean delete(ProductDTO mDTO);
}

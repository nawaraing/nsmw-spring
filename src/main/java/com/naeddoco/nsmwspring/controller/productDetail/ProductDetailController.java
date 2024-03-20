package com.naeddoco.nsmwspring.controller.productDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

@Controller
public class ProductDetailController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/productDetail")
    public String getProductDetail(@RequestParam("productID") int productID, Model model, ProductDTO productDTO) {
	
		productDTO.setSearchCondition("getProductDetail");
		productDTO.setProductID(productID);
		
		productDTO = productService.selectOne(productDTO);
		
		model.addAttribute("productDetail", productDTO);
		
        return "user/productDetail"; // 이동할 페이지의 이름을 반환합니다.
        
    }

}

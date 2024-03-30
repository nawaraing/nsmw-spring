package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.productModel.ProductDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import jakarta.servlet.http.HttpSession;

// 상품을 검색/정렬하는 컨트롤러

@Controller
public class SearchAndSortProductController {

	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value = "/productList/searchAndSort", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductDTO> searchAndSortProductList(HttpSession session,
										   @RequestParam("searchKeyword") String searchKeyword, 
										   @RequestParam("sortColumnName") String sortColumnName) {
		
		// -----------------------------------------------검색 조건 및 정렬 조건에 맞는 데이터 습득 ↓-----------------------------------------------
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setSearchCondition("selectAdminProductListDatas"); // 쿼리 분기명 set
		
		searchKeyword = searchKeyword.trim(); // 검색 키워드의 공백 제거
		productDTO.setSearchKeyword("%"+searchKeyword+"%"); // 검색 키워드를 set
		
		// 정렬 기준이 될 컬럼 결정, 오름차순/내림차순 결정 분기
		if(sortColumnName.equals("salePrice")) { 
			
			productDTO.setSortColumnName("SALE_PRICE");
			productDTO.setSortMode("DESC"); // 내림차순 set
			
		} else if(sortColumnName.equals("registerDate")) {
			
			productDTO.setSortColumnName("REGISTER_DATE");
			productDTO.setSortMode("ASC"); // 오름차순 set
			
		} else if(sortColumnName.equals("saleState")) {
			
			productDTO.setSortColumnName("SALE_STATE");
			productDTO.setSortMode("DESC"); // 내림차순 set
		
		} else {
			
			productDTO.setSortColumnName("REGISTER_DATE");
			productDTO.setSortMode("ASC");
			
		}
		
		List<ProductDTO> productDTOList = productDAO.selectAll(productDTO);
		
		return productDTOList;

	}

}
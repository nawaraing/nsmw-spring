package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDAO;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import jakarta.servlet.http.HttpSession;

// 상품 목록 페이지에 진입하는 컨트롤러

@Controller
public class EntryProductListPageController {
	
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping(value = "/productList", method = RequestMethod.GET)
	public String entryProductList(HttpSession session, Model model) {
		
		//-----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		//-----------------------------------------------모든 상품 정보를 습득-----------------------------------------------
		
		 ProductDTO productDTO = new ProductDTO();
		
		productDTO.setSearchCondition("selectAdminProductListDatas"); // 쿼리 분기명 set
		productDTO.setSearchKeyword("%%"); // 검색 키워드 set
		productDTO.setSortColumnName("REGISTER_DATE"); // 정렬할 컬럼명 set
		productDTO.setSortMode("ASC"); // 정렬 방식 set
		
		List<ProductDTO> productDTOList = productDAO.selectAll(productDTO);
		
		model.addAttribute("productList", productDTOList);
		
		//-----------------------------------------------모든 카테고리 정보를 습득-----------------------------------------------
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		List<CategoryDTO> categoryDTOList = categoryDAO.selectAll(categoryDTO);
		
		model.addAttribute("categoryList", categoryDTOList);
		
		return "admin/productList"; // 장바구니 페이지로 요청

	}

}

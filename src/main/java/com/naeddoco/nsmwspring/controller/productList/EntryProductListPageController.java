package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryService;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 상품 목록 페이지에 진입하는 컨트롤러

@Controller
@Slf4j
public class EntryProductListPageController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/productList", method = RequestMethod.GET)
	public String entryProductList(HttpSession session, Model model) {
		
		//-----------------------------------------------세션 확인 ↓-----------------------------------------------

		String authority = (String) session.getAttribute("authority");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (!authority.equals("ADMIN")) {
			
			log.debug("회원 등급이 관리자(ADMIN)가 아니여서 메인페이지로 이동");
			
			return "redirect:/";
		}	
		
		//-----------------------------------------------모든 상품 정보를 습득-----------------------------------------------
		
		 ProductDTO productDTO = new ProductDTO();
		
		productDTO.setSearchCondition("selectAdminProductListDatas"); // 쿼리 분기명 set
		productDTO.setSearchKeyword("%%"); // 검색 키워드 set
		productDTO.setSortColumnName("REGISTER_DATE"); // 정렬할 컬럼명 set
		productDTO.setSortMode("DESC"); // 정렬 방식 set
		
		List<ProductDTO> productDTOList = productService.selectAll(productDTO);
		
		model.addAttribute("productList", productDTOList);
		
		//-----------------------------------------------모든 카테고리 정보를 습득-----------------------------------------------
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		List<CategoryDTO> categoryDTOList = categoryService.selectAll(categoryDTO);
		
		model.addAttribute("categoryList", categoryDTOList);
		
		return "admin/productList"; // 장바구니 페이지로 요청

	}

}

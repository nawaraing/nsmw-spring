package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StopSaleProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/productList/stop", method = RequestMethod.POST)
	public String stopSaleProduct(HttpSession session, @RequestParam("productID") List<Integer> productIDs) {
		
		//-----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		//-----------------------------------------------판매 상태 변경 ↓-----------------------------------------------
		
		for(int i = 0; i < productIDs.size(); i ++) {
			
			ProductDTO productDTO = new ProductDTO();
			productDTO.setSearchCondition("updateSaleState");
			productDTO.setProductID(productIDs.get(i));
			productDTO.setSaleState("DISCONTINUED");
			
			productService.update(productDTO);
			
		}
		
		return "redirect:/productList";
		
	}

}

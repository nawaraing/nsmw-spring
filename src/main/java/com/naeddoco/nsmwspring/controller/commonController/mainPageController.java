package com.naeddoco.nsmwspring.controller.commonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

@Controller
public class mainPageController {
	
	@Autowired
	private MemberService memberService;
	private ProductService productService;
	
	@GetMapping("/")
	public String test(Model model, ProductDTO productDTO, MemberDTO memberDTO) {
		
		
		
		
		return "index";
			
	}

}

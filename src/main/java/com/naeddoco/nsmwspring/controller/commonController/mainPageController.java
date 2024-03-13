package com.naeddoco.nsmwspring.controller.commonController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

@Controller
public class mainPageController {
	
	@GetMapping("/")
	public String test(Model model, ProductDTO productDTO) {

		return "main";
			
	}

}

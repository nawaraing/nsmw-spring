package com.naeddoco.nsmwspring.controller.commonController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoDTO;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoService;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class mainPageController {

	@Autowired
	private OrderInfoService orderService;

	@GetMapping("/")
	public String test(HttpSession session, Model model, ProductDTO productDTO, ProductCategoryDTO productCategoryDTO, OrderInfoDTO orderDTO) {

		// 세션에서 사용자 이름 가져오기
		String member = (String) session.getAttribute("member");

		if (member == null) { // 유저의 세션 정보가 없을 경우
			
			orderDTO.setSearchCondition("getUpperEight");
			
			List<OrderInfoDTO> orderDTOList = orderService.selectAll(orderDTO);
			
			model.addAttribute("rcmDTOs", orderDTOList);
			
		} else { // 유저의 세션 정보가 있을 경우

			
			
		}

		return "main";

	}

}
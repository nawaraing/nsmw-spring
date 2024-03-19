package com.naeddoco.nsmwspring.controller.commonController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoDTO;
import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoService;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoDTO;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class mainPageController {

	@Autowired
	private OrderInfoService orderService;
	@Autowired
	private BuyInfoService buyInfoService;

	@GetMapping("/main")
	public String test(HttpSession session, Model model, OrderInfoDTO orderDTO, BuyInfoDTO buyInfoDTO) {

		String member = (String) session.getAttribute("member"); // 세션에서 사용자 이름 가져오기
		
		orderDTO.setSearchCondition("getBestEight"); // orderDTO검색 조건 설정
		
		List<OrderInfoDTO> orderDTOList = orderService.selectAll(orderDTO); // selectAll 실행 후 반환 값 저장

		if (member == null) { // 유저의 세션 정보가 없을 경우
			
			model.addAttribute("recommandProducts", orderDTOList); // 메인 상단 상품 리스트 정보
			
		} else { // 유저의 세션 정보가 있을 경우
			
			
			// 구매까지 구현후에 알맞은 알고리즘 추천으로 변경이 필요
			model.addAttribute("recommandProducts", orderDTOList); // 메인 상단 상품 리스트 정보
						
		}
		
		model.addAttribute("allProducts", orderDTOList); // 메인 하단 상품 리스트 정보
		
		return "user/main";

	}

}
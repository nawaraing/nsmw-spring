package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BuyInfoPageController {
	
	@RequestMapping(value = "/user/buyInfoPage", method = RequestMethod.GET)
	public String buyInfoPageController(Model model, HttpSession session) {
		
		String memberID = (String) session.getAttribute("memberID");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (memberID == null) {
			
			log.debug("[log] InsertCart 로그아웃상태");
			
			return "redirect:/";
		}
		
		log.debug("[Page이동] 마이페이지 -> 구매내역페이지");
		
		model.addAttribute("pageValue", "구매내역");
			
		return "/user/buyInfo";
	}

}

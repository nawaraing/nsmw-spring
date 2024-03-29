package com.naeddoco.nsmwspring.controller.buy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BuyInfoPageController {
	
	@RequestMapping(value = "/user/buyInfoPage", method = RequestMethod.GET)
	public String buyInfoPageController() {
		
		System.out.println("[Page이동] 마이페이지 -> 구매내역페이지");
			
		return "/user/buyInfo";
	}

}

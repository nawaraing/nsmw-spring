package com.naeddoco.nsmwspring.controller.statDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryStatDatePageController {
	
	@RequestMapping(value = "/entryStatDate", method = RequestMethod.GET)
	public String entryCart() {

		return "admin/statDate"; // 장바구니 페이지로 요청

	}

}

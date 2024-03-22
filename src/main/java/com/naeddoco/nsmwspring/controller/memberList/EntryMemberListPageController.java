package com.naeddoco.nsmwspring.controller.memberList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryMemberListPageController {
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String entryCart() {

		return "admin/memberList"; // 장바구니 페이지로 요청

	}

}

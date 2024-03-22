package com.naeddoco.nsmwspring.controller.productList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryProductListPageController {
	
	@RequestMapping(value = "/entryProductList", method = RequestMethod.GET)
	public String entryCart() {

		return "admin/productList"; // 장바구니 페이지로 요청

	}

}

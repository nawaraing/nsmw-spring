package com.naeddoco.nsmwspring.controller.couponBatch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryCouponBatchPageController {
	
	@RequestMapping(value = "/entryCouponBatch", method = RequestMethod.GET)
	public String entryCart() {

		return "admin/couponBatch"; // 장바구니 페이지로 요청

	}

}

package com.naeddoco.nsmwspring.controller.couponGrade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryCouponGradePageController {
	
	@RequestMapping(value = "/entryCouponGrade", method = RequestMethod.GET)
	public String entryCart() {

		return "admin/couponGrade"; // 장바구니 페이지로 요청

	}

}

package com.naeddoco.nsmwspring.controller.couponDownload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryCouponDownloadPageController {
	
	@RequestMapping(value = "/entryCouponDownload", method = RequestMethod.GET)
	public String entryCart() {

		return "admin/couponDownload"; // 장바구니 페이지로 요청

	}

}

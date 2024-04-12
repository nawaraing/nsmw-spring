package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntryReviewPageController {
	
	@RequestMapping(value = "/user/reviewInfo", method = RequestMethod.GET )
	public String entryReviewPage (Model model) {
		
		log.debug("[페이지 이동] 마이페이지 → 리뷰내역");
		
		model.addAttribute("pageValue", "리뷰내역");
		
		return "user/reviewInfo";
	}
	
}

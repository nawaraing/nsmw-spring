package com.naeddoco.nsmwspring.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TermsPageController {
	
	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public String termsPage() {
		
		log.debug("TermsPageController 약관페이지 이동");
		
		return "user/terms";
		
	}

}

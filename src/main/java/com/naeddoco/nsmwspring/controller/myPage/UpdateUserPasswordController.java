package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UpdateUserPasswordController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/user/modifyUserPassword", method = RequestMethod.POST)
	public String updateUserPasswordController(MemberDTO memberDTO, HttpServletRequest request) {
		
		log.debug("/user/modifyUserPassword 요청 성공");
		
		HttpSession session = request.getSession();
		
		// 회원 아이디가 일치하는 row의 값을 업데이트
		String memberID = (String) session.getAttribute("memberID");
		// 변경하려는 PW
		String memberPassword = request.getParameter("memberPassword");
	    
	    memberDTO.setSearchCondition("memberPasswordUpdate");
	    memberDTO.setMemberID(memberID);
	    memberDTO.setMemberPassword(memberPassword);
		
	    log.debug("변경할 PW : " + memberPassword);
	    
	    boolean result = memberService.update(memberDTO);
	    
	    if(!result) {
	    
	    	log.debug("[회원정보 변경] 실패");
	    	
	    	return "/user/myPage";
	    	
	    }
		
		log.debug("[회원정보 변경] 성공");

		return "redirect:/logout";
	}

}

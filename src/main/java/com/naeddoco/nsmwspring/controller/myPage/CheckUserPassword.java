package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;
import com.naeddoco.nsmwspring.model.shippingAddressModel.ShippingAddressDTO;
import com.naeddoco.nsmwspring.model.shippingAddressModel.ShippingAddressService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckUserPassword {
	
	@Autowired
	private MemberService memberService;

	// 회원정보 변경 시 비밀번호 확인하는 페이지로 이동
	@RequestMapping(value = "user/checkUserPasswordPage", method = RequestMethod.GET)
	public String checkUserPasswordPage(@RequestParam("where") String where, Model model) {
		
		System.out.println("[checkUserPasswordPage] 진입");
		
		System.out.println("where의 값 : " + where);

		model.addAttribute("where", where);
		
		return "user/checkUserPassword";
		
	}
	
	// 입력된 비밀번호가 일치하는지 확인 후 결과를 반환
	@RequestMapping(value = "/checkPw", method = RequestMethod.POST)
	public @ResponseBody String ancCheckUserPassword(MemberDTO memberDTO, Model model, HttpSession session,
															@RequestParam("memberPassword") String memberPassword) {
		
		System.out.println("[ancCheckUserPassword] 진입");

		String memberID = (String) session.getAttribute("memberID");
		
		System.out.println("[회원ID] " + memberID);
		System.out.println("[회원PW] " + memberPassword);
		
		memberDTO.setSearchCondition("memberLogin");
		memberDTO.setMemberID(memberID);
		memberDTO.setMemberPassword(memberPassword);
		memberDTO = memberService.selectOne(memberDTO);
		
		if( memberDTO == null ) {
			
			System.out.println("fail 반환");
			
			return "fail";
			
		}
		
		System.out.println("회원정보 : " + memberDTO.toString());

		System.out.println("suc 반환");
		
		return "suc";
	}

}

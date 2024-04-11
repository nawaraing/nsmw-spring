package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;
import com.naeddoco.nsmwspring.model.shippingAddressModel.ShippingAddressDTO;
import com.naeddoco.nsmwspring.model.shippingAddressModel.ShippingAddressService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UpdateUserInfoController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@RequestMapping(value = "/user/modifyUserInfo", method = RequestMethod.POST)
	public String updateUserInfoController(MemberDTO memberDTO, ShippingAddressDTO shippingAddressDTO, HttpServletRequest request) {
		
		log.debug("/user/modifyUserInfo 요청 성공");
		
		HttpSession session = request.getSession();
		
		// 회원 아이디가 일치하는 row의 값을 업데이트
		String memberID = (String) session.getAttribute("memberID");
		
		String phoneNumber1 = (String) request.getParameter("phoneNumber1");
		String phoneNumber2 = (String) request.getParameter("phoneNumber2");
		String phoneNumber3 = (String) request.getParameter("phoneNumber3");
		log.debug("[전화번호 前] " + phoneNumber1);
		log.debug("[전화번호 中] " + phoneNumber2);
		log.debug("[전화번호 後] " + phoneNumber3);

		
		String phoneNumber = phoneNumber1 +"-"+ phoneNumber2 +"-"+ phoneNumber3;
		log.debug("[전화번호 完] " + phoneNumber);
		
	    String email1 = (String) request.getParameter("email1");
	    String email2 = (String) request.getParameter("email2");
	    log.debug("[이메일 前] " + email1);
	    log.debug("[이메일 後] " + email2);
	    
	    String email = email1 + "@" + email2;
	    System.out.println("[이메일 完] " + email);
	    
	    memberDTO.setSearchCondition("memberInfoUpdate");
	    memberDTO.setMemberID(memberID);
	    memberDTO.setPhoneNumber(phoneNumber);
	    memberDTO.setEmail(email);
		
	    log.debug("변경할 정보 : " + memberDTO.toString());
	    
	    // 주소 변경
	    String ShippingAddressID = request.getParameter("ancShippingAddressID");
	    int ancShippingAddressID = Integer.parseInt(ShippingAddressID);
	    
	    String ShippingPostCode = request.getParameter("ancShippingPostCode");
	    int ancShippingPostCode = Integer.parseInt(ShippingPostCode);
	    String ancShippingAddress = request.getParameter("ancShippingAddress");
	    String ancShippingAddressDetail = request.getParameter("ancShippingAddressDetail");
	    
		log.debug("[주소PK] " + ShippingPostCode);
		log.debug("[우편번호] " + ancShippingPostCode);
		log.debug("[주소] " + ancShippingAddress);
		log.debug("[주소 상세] " + ancShippingAddressDetail);	    
	    
	    shippingAddressDTO.setSearchCondition("memberAddressUpdate");
	    shippingAddressDTO.setShippingAddressID(ancShippingAddressID);
	    shippingAddressDTO.setShippingPostcode(ancShippingPostCode);
	    shippingAddressDTO.setShippingAddress(ancShippingAddress);
	    shippingAddressDTO.setShippingDetailAddress(ancShippingAddressDetail);	    
	    
	    boolean resultAddressUpdate = shippingAddressService.update(shippingAddressDTO);	    
	    
	    boolean resultMemberUpdate = memberService.update(memberDTO);
	    
	    if(!resultMemberUpdate) {
	    
	    	log.debug("[회원정보 변경] 실패");
	    	
	    	return "redirect:/user/myPage";
	    	
	    }
	    
	    if(!resultAddressUpdate) {

	    	log.debug("[회원주소 변경] 실패");
	    	
	    	return "redirect:/user/myPage";
	    }
		
		log.debug("[회원정보 변경] 성공");

		return "redirect:/logout";
	}

}

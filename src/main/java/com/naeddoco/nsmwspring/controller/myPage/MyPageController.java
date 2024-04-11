package com.naeddoco.nsmwspring.controller.myPage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberCategoryModel.MemberCategoryDTO;
import com.naeddoco.nsmwspring.model.memberCategoryModel.MemberCategoryService;
import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyPageController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberCategoryService memberCategoryService;

	@RequestMapping(value = "/user/myPage", method = RequestMethod.GET)
	public String myPage(MemberDTO memberDTO, MemberCategoryDTO memberCategoryDTO, HttpSession session, Model model) {

		log.debug("[log] MyPageController 진입");

		String memberID = (String) session.getAttribute("memberID");

		log.debug("[log] MyPageController 로그인 아이디 : " + memberID);

		memberCategoryDTO.setSearchCondition("memberCategory");
		memberCategoryDTO.setMemberID(memberID);
		List<MemberCategoryDTO> memberCategoryList = memberCategoryService.selectAll(memberCategoryDTO);

		memberDTO.setSearchCondition("myPageMain");
		memberDTO.setMemberID(memberID);
		memberDTO = memberService.selectOne(memberDTO);
		
		log.debug("[myPage] memberDTO 정보 : " + memberDTO.getPhoneNumber());
		log.debug("[myPage] memberDTO 정보 : " + memberDTO.getAncShippingAddressID());

		if (memberCategoryList.size() < 1) {
			
			log.debug("[MyPageController] 회원 카테고리 없음");

			memberDTO.setAncCategoryName("");
			
		} else {
			
			log.debug("[MyPageController] 회원 카테고리 있음");
			
			String memberCategory = "";
			
			for (int i = 0; i < memberCategoryList.size(); i++) {
				
				memberCategory = memberCategory +"     " + memberCategoryList.get(i).getAncCategoryName();
				
			}
			
			memberDTO.setAncCategoryName(memberCategory);
			
			log.debug("회원 카테고리 : " + memberDTO.getAncCategoryName());
			
		}

		model.addAttribute("memberInfo", memberDTO);

		return "/user/myPage";
	}

}

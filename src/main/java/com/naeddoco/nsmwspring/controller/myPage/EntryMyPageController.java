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
public class EntryMyPageController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberCategoryService memberCategoryService;

	@RequestMapping(value = "/user/myPage", method = RequestMethod.GET)
	public String entryMyPage (MemberDTO memberDTO, MemberCategoryDTO memberCategoryDTO, HttpSession session, Model model) {

		log.debug("[log] MyPageController 진입");

		String memberID = (String) session.getAttribute("memberID");

		// 회원이 로그인 상태가 아니라면 false 반환
		if (memberID == null) {
			
			log.debug("[log] InsertCart 로그아웃상태");
			
			return "redirect:/";
		}
		
		log.debug("[log] MyPageController 로그인 아이디 : " + memberID);

		memberCategoryDTO.setSearchCondition("memberCategory");
		memberCategoryDTO.setMemberID(memberID);
		List<MemberCategoryDTO> memberCategoryList = memberCategoryService.selectAll(memberCategoryDTO);

		memberDTO.setSearchCondition("myPageMain");
		memberDTO.setMemberID(memberID);
		memberDTO = memberService.selectOne(memberDTO);
		
		log.debug("[myPage] memberDTO 정보 : " + memberDTO.getPhoneNumber());
		log.debug("[myPage] memberDTO 정보 : " + memberDTO.getAncShippingAddressID());
		
		if (memberDTO.getGender() != null) {
			
			if(memberDTO.getGender().equals("MALE")) {
				
				memberDTO.setGender("남");
				
			} else if (memberDTO.getGender().equals("FEMALE")) {
				
				memberDTO.setGender("여");
				
			}
			
		} else {
			
			log.debug("회원 성별이 없거나 남, 여 외 다른 값");
			
		}

		if (memberDTO.getAncCategoryName() != null) {
			
			log.debug("[MyPageController] 회원 카테고리 있음");
			
			String memberCategory = "";
			
			for (int i = 0; i < memberCategoryList.size(); i++) {
				if (!memberCategory.equals("")) {
					memberCategory += ", ";
				}
				memberCategory += memberCategoryList.get(i).getAncCategoryName();
			}
			
			memberDTO.setAncCategoryName(memberCategory);
			
			log.debug("회원 카테고리 : " + memberDTO.getAncCategoryName());			
			
		} else {
						
			log.debug("[MyPageController] 회원 카테고리 없음");

			memberDTO.setAncCategoryName("관심 카테고리가 없습니다.");
			
		}

		model.addAttribute("memberInfo", memberDTO);

		return "/user/myPage";
	}

}

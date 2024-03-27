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

@Controller
public class MyPageController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberCategoryService memberCategoryService;

	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String myPage(MemberDTO memberDTO, MemberCategoryDTO memberCategoryDTO, HttpSession session, Model model) {

		System.out.println("[log] MyPageController 진입");

		String memberID = (String) session.getAttribute("memberID");

		System.out.println("[log] MyPageController 로그인 아이디 : " + memberID);

		memberCategoryDTO.setSearchCondition("memberCategory");
		memberCategoryDTO.setMemberID(memberID);
		List<MemberCategoryDTO> memberCategoryList = memberCategoryService.selectAll(memberCategoryDTO);

		memberDTO.setSearchCondition("myPageMain");
		memberDTO.setMemberID(memberID);
		memberDTO = memberService.selectOne(memberDTO);

		if (memberCategoryList.size() < 1) {
			
			System.out.println("[MyPageController] 회원 카테고리 없음");

			memberDTO.setAncMemberCategoryName("");
			
		} else {
			
			System.out.println("[MyPageController] 회원 카테고리 있음 ");
			
			String memberCategory = "";
			
			for (int i = 0; i < memberCategoryList.size(); i++) {
				
				memberCategory = memberCategory +"     " + memberCategoryList.get(i).getAncCategoryName();
				
			}
			
			memberDTO.setAncMemberCategoryName(memberCategory);
			
			System.out.println("회원 카테고리 : " + memberDTO.getAncMemberCategoryName());
			
		}

		model.addAttribute("memberInfo", memberDTO);

		return "user/myPage";
	}

}

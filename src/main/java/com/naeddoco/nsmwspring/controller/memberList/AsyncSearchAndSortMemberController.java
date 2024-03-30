package com.naeddoco.nsmwspring.controller.memberList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.memberModel.MemberDAO;
import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

import jakarta.servlet.http.HttpSession;

// 회원 목록 페이지 검색/정렬 컨트롤러

@Controller
@ResponseBody
public class AsyncSearchAndSortMemberController {
	
	@Autowired
	MemberDAO memberDAO = new MemberDAO();
	
	@RequestMapping(value = "/memberList/searchAndSort", method = RequestMethod.GET)
	public List<MemberDTO> searchAndSortMember(HttpSession session, 
									  		   Model model,
									  		   @RequestParam("searchKeyword") String searchKeyword,
									  		   @RequestParam("sortColumnName") String sortColumnName) {
			
		//-----------------------------------------------조건에 맞는 유저 습득 ↓-----------------------------------------------
			
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setSearchCondition("selectAdminMemberListDatas");
		memberDTO.setSearchKeyword("%"+searchKeyword+"%");
		memberDTO.setSortColumnName("M.MEMBER_ID");
		
		if(sortColumnName.equals("ASC")) {
			
			memberDTO.setSortMode("ASC");
			
		} else if(sortColumnName.equals("DESC")) {
			
			memberDTO.setSortMode("DESC");
			
		} else {
			
			memberDTO.setSortMode("ASC");
			
		}
		
		System.out.println(memberDTO);
		
		List<MemberDTO> memberDTOList = memberDAO.selectAll(memberDTO);
		
		return memberDTOList;
		
	}

}
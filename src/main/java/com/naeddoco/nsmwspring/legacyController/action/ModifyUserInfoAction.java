package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.MemberDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ModifyUserInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-회원정보변경페이지-회원정보 변경 로직 
		 * 개인정보에서 변경할 값 : mName/phoneNumber/email/address
		 */
		ActionForward forward = new ActionForward();
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		//세션의 MID와 입력받은 mName/phoneNumber/email/address 받아오기
		HttpSession session = request.getSession();
		String MID = (String)session.getAttribute("member");
		String mName = request.getParameter("mName");

		// String 조합
		String phoneNumber = request.getParameter("phoneNum1") + "-" + request.getParameter("phoneNum2") + "-" + request.getParameter("phoneNum3");
		String email = request.getParameter("email1") + "@" + request.getParameter("email2");

		String zipNo = request.getParameter("zipcode");
		String roadAddrPart1 = request.getParameter("address1");
		String addrDetail = request.getParameter("address2");
		
		//입력받은 정보를 mDTO에 담아 DB에 update
		mDTO.setSearchCondition("회원정보변경");
		mDTO.setMID(MID);
		mDTO.setmName(mName);
		mDTO.setPhoneNumber(phoneNumber);
		mDTO.setEmail(email);
		mDTO.setmAddress(roadAddrPart1);
		mDTO.setmDetailedAddress(addrDetail);
		mDTO.setmPostCode(Integer.parseInt(zipNo));
//		System.out.println("update memberDTO: " + mDTO);
		//입력받은 회원정보 update
		boolean result = mDAO.update(mDTO);
		
		if(result) {
			//개인정보수정 성공
//			System.out.println("[log] ModifyUserInfoAction 개인정보 수정 성공!");
			forward.setPath("mypage.do");
			forward.setRedirect(false);
		}else {
			//개인정보수정 실패 -> DB접속이 끊겼거나, error인 상황
//			System.out.println("[log] ModifyUserInfoAction 개인정보 수정 실패");
		}
		return forward;
	}

}

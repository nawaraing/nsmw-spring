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

public class CheckUserPasswordAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-개인정보수정/비밀번호변경 선택시 
		 * 현재 비밀번호 일치여부 확인 로직
		 */
		ActionForward forward = new ActionForward();
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		//세션의 MID 받아오기
		HttpSession session = request.getSession();
		String MID = (String)session.getAttribute("member");
		//V에서 입력한 비밀번호 받아오기
		String MPW = request.getParameter("mPassword");
		
		//DB에서 비밀번호 일치여부 확인
		mDTO.setMID(MID);
		mDTO.setmPassword(MPW);
		mDTO.setSearchCondition("비밀번호확인");	
		mDTO = mDAO.selectOne(mDTO);

		//비밀번호가 일치하면
		//어디로 보낼지 (modifyUserInfo or modifyUserPassword) 판단
		//V에서 GET방식으로 전달된 where값 받아오기
		String getPath = request.getParameter("where");
		
		if (mDTO != null) {
			// 비밀번호 일치
//			System.out.println("[log] CheckUserPasswordAction 비밀번호 일치!");
			//where값에 따른 경로 설정
			if(getPath.equals("modifyUserInfo")) {
				forward.setPath("modifyUserInfoPage.do");
				forward.setRedirect(false);
			}else if(getPath.equals("modifyUserPassword")) {
				forward.setPath("modifyUserPasswordPage.do");
				forward.setRedirect(true);
			}else {
				forward.setPath("error.do");
				forward.setRedirect(true);
			}
		} else {
			//비밀번호 불일치
//			System.out.println("[log] CheckUserPasswordAction 비밀번호 불일치");
			//비밀번호 불일치 결과 전달
			request.setAttribute("checkResult", false);
			//비밀번호확인 페이지로 이동
			forward.setPath("checkUserPasswordPage.do");
			forward.setRedirect(false);
		}
		return forward;
	}

}

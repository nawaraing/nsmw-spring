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

public class MypageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-로그인된 회원 정보 출력 로직
		 */
		ActionForward forward = new ActionForward();

//		필요정보 : 이름/성별/생년월일/휴대폰번호/이메일/건강상태
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();

		HttpSession session = request.getSession();
		String MID = (String) session.getAttribute("member");

		// DB에서 마이페이지(이름, 생년월일, 성별, 전화번호, 이메일, 주소) 정보 받아오기
		mDTO.setMID(MID);
		mDTO.setSearchCondition("회원정보");
		mDTO = mDAO.selectOne(mDTO);
		
		//DB에서 확인한 회원정보 전달
		request.setAttribute("memberInfo", mDTO);
//		System.out.println("[MypageAction] mDTO : " + mDTO);

		String health = mDTO.getHealth();
		if (health == null) {
			mDTO.setHealth("정보 미입력");
		} else {
			// "간;눈;면역;" -> "간, 눈, 면역"
			if (!health.endsWith(";")) {
//				System.out.println("[MypageAction] health += ;");
				health += ";";
			}
			health = health.replace(";", ", ");
			mDTO.setHealth(health.substring(0, health.length() - 2));
		}
		//정보를 보낼 경로와 redirect방식 설정
		forward.setPath("myPage.jsp");
		forward.setRedirect(false);

		return forward;
	}

}

package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.BuyInfoDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.BuyInfoDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BuyInfoPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-구매내역 조회 로직
		 */
		ActionForward forward = new ActionForward();
		
		BuyInfoDAO bDAO = new BuyInfoDAO();
		BuyInfoDTO bDTO = new BuyInfoDTO();
		ArrayList<BuyInfoDTO> bDTOs = new ArrayList<BuyInfoDTO>();
		//세션의 member의 MID가져오기
		HttpSession session = request.getSession();
		String MID = (String)session.getAttribute("member");
		
		//DB에서 해당 MID의 구매내역 가져오기 
		bDTO.setSearchCondition("구매내역");
		bDTO.setMID(MID);
		bDTOs = bDAO.selectAll(bDTO);
//		System.out.println("[BuyInfoPageAction] bDTOs : " + bDTOs);
		//조회된 구매내역 V로 전달
		request.setAttribute("bDTOs", bDTOs);
		
		//정보를 보낼 경로와 redirect방식 설정
		forward.setPath("buyInfo.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}

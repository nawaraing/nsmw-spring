package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.CartDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.CartDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CartDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 장바구니페이지-장바구니 상품 삭제 로직
		 */
		
		ActionForward forward = new ActionForward();
		
		//DB에서 선택한 상품 삭제
		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		
		//V에서 선택된 상품의 CID 받아오기
		String strCid = (String)request.getParameter("CID");
//		System.out.println("[log] cart delete strCid: " + strCid);
		int intCid = Integer.parseInt(strCid);
		
		cDTO.setSearchCondition("장바구니비우기");
		cDTO.setCID(intCid);
		if (cDAO.delete(cDTO)) {
			forward.setPath("cartPage.do");
			forward.setRedirect(true);
		} else {
			forward.setPath("error.do");
			forward.setRedirect(true);
		}
		
		return forward;
	}

}

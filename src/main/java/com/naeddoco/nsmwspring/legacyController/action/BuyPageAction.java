package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.CouponDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.MemberDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.ProductDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.CouponDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.ProductDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BuyPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 결제페이지-로그인된 회원의 정보와 쿠폰, 장바구니 정보 반환 로직
		 */
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		
		CouponDTO cpDTO = new CouponDTO();
		CouponDAO cpDAO = new CouponDAO();
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		ProductDAO productDAO = new ProductDAO();

		//세션의 MID가져오기
		String MID = (String)session.getAttribute("member");
		
		//회원정보가져오기
		mDTO.setMID(MID);
		mDTO.setSearchCondition("주문정보");
		mDTO = mDAO.selectOne(mDTO);
		request.setAttribute("memberInfo", mDTO);
//		System.out.println("[log] mDTO : " + mDTO);

		//쿠폰목록 조회시 미사용_기간만료 쿠폰 삭제하고 가져오기
		cpDTO.setMID(MID);
		cpDTO.setSearchCondition("쿠폰삭제");
		boolean result = cpDAO.delete(cpDTO);
		//쿠폰삭제 결과 로그 출력
		if(result) {
//			System.out.println("[log] 기간만료_미사용쿠폰 삭제 완료!");
		}else {
//			System.out.println("[log] 기간만료_미사용쿠폰 없음");
		}
		//사용가능 쿠폰 조회
		cpDTO.setSearchCondition("사용가능쿠폰");
//		System.out.println("[log] cpDTO.getMID() :  " + cpDTO.getMID());
		ArrayList<CouponDTO> couponList = cpDAO.selectAll(cpDTO);
		//조회된 쿠폰 전달
		request.setAttribute("couponList", couponList);
//		System.out.println("[log] couponList : " + couponList);

		//장바구니 상품은 체크박스로 선택된 상품만 배열로 각각 정보가 넘어옴
		String[] PIDs = request.getParameterValues("PID[]");
		String[] CIDs = request.getParameterValues("CID[]");
		String[] imagePaths = request.getParameterValues("imagePath[]");
		String[] pNames = request.getParameterValues("pName[]");
		String[] sellingPrices = request.getParameterValues("sellingPrice[]");
		String[] cQtys = request.getParameterValues("cQty[]");

		/* 로그
		for (int i = 0; i < imagePaths.length; i++) {
		   System.out.println("[log] imagePaths : " + imagePaths[i]);
		}
		for (int i = 0; i < pNames.length; i++) {
		   System.out.println("[log] pNames : " + pNames[i]);
		}
		for (int i = 0; i < cQtys.length; i++) {
		   System.out.println("[log] cQtys : " + cQtys[i]);
		}
		for (int i = 0; i < cQtys.length; i++) {
		   System.out.println("[log] sellingPrices : " + sellingPrices[i]);
		}
		*/
		//장바구니에서 선택된 상품정보 확인
		ArrayList<ProductDTO> selectedProductsList = new ArrayList<>();
		for (int i = 0; i < PIDs.length; i++) {
			ProductDTO productDTO = new ProductDTO();
			int PID = Integer.parseInt(PIDs[i]);
			String imagePath = imagePaths[i];
			String pName = pNames[i];
			int sellingPrice = Integer.parseInt(sellingPrices[i]);
			int cQty = Integer.parseInt(cQtys[i]);
			int CID = Integer.parseInt(CIDs[i]);

			//DB에서 category 가져오기
			productDTO.setSearchCondition("카테고리");
			productDTO.setPID(PID);
			productDTO = productDAO.selectOne(productDTO);
//			System.out.println("[log] productDTO category : " + productDTO);
			
			//V에서 받아온 CID/imagePath/pName/cQty/sellingPrice + category -> selectedProductsList에 담기
			productDTO.setPID(PID);
			productDTO.setAncCID(CID);
			productDTO.setImagePath(imagePath);
			productDTO.setpName(pName);
			productDTO.setpQty(cQty);
			productDTO.setSellingPrice(sellingPrice);
			selectedProductsList.add(productDTO);
		}
		//selectedProductsList를 V에 전달    
		request.setAttribute("selectedProductsList", selectedProductsList);
		
		//로그
//		for (ProductDTO productDTOs : selectedProductsList) {
//			System.out.println("[log] productDTOs : " + productDTOs);
//		}
		//정보를 보낼 경로와 redirect방식 설정
		forward.setPath("buy.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
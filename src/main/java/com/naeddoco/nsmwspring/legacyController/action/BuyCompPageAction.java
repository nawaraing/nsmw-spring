package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.BuyInfoDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.CartDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.CouponDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.BuyInfoDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.CartDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.CouponDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BuyCompPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();

		/*
		 * Ctrl : BuyInfo insert(구매내역 추가), Coupon update(쿠폰 사용 처리), Cart delete(구매한 상품 장바구니 제거)
		 * Ctrl -> View : buyComp.jsp
		 * */
		

		/*
		 * 구매 로직 시작
		 */
		BuyInfoDTO bDTO = new BuyInfoDTO();
		BuyInfoDAO bDAO = new BuyInfoDAO();
		ArrayList<BuyInfoDTO> bDTOs = new ArrayList<BuyInfoDTO>();

		// 주문번호 가져오기
		bDTO.setSearchCondition("주문번호");
		bDTO = bDAO.selectOne(bDTO);
		// int orderNum = bDTO.getOrderNum(); // bDTO에 포함된 상태

		// 구매내역 추가
		bDTO.setSearchCondition("구매내역추가");

		// MID 가져오기
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("member");
		bDTO.setMID(mid);

		// 잘못된 mPostcode => error 페이지
		bDTO.setbPostCode(Integer.parseInt(request.getParameter("bPostcode")));
		bDTO.setbAddress(request.getParameter("bAddress"));
		bDTO.setbDetailedAddress(request.getParameter("bDetailedAddress"));


		// 상품 관련 정보
		String[] PIDs = request.getParameterValues("PID[]");
		String[] CIDs = request.getParameterValues("CID[]");
		String[] sellingPrices = request.getParameterValues("sellingPrice[]");
		String[] productCategories = request.getParameterValues("productCategory[]");
		String[] qtys = request.getParameterValues("qty[]");

		// 쿠폰 관련 정보
		String[] CPIDs = request.getParameterValues("CPID[]");
		//System.out.println("[BuyCompPage] CPIDs : " + CPIDs.length);
		String[] discounts = request.getParameterValues("discount[]");
		//System.out.println("[BuyCompPage] discounts : " + discounts.length);
		String[] couponCategories = request.getParameterValues("couponCategory[]");
		//System.out.println("[BuyCompPage] couponCategories : " + couponCategories.length);

		int PID = 0;
		int CID = 0;
		int sellingPrice = 0;
		String productCategory = "";
		int qty = 0;

		String CPID = "";
		int discount = 0;
		String couponCategory = "";
		for (int i = 0; i < PIDs.length; i++) {
			System.out.println("[BuyCompPage] PIDs[i] : " + PIDs[i]);
			PID = Integer.parseInt(PIDs[i]);
			System.out.println("[BuyCompPage] CIDs[i] : " + CIDs[i]);
			CID = Integer.parseInt(CIDs[i]);
			sellingPrice = Integer.parseInt(sellingPrices[i]);
			productCategory = productCategories[i];
			qty = Integer.parseInt(qtys[i]);

			// 쿠폰 적용되는 price 계산
			if(CPIDs != null) {
				CPID = "";
				for (int j = 0; j < CPIDs.length; j++) {
					CPID = CPIDs[j];
//					System.out.println("[BuyCompPage] CPID [" + CPID + "]");
					discount = Integer.parseInt(discounts[j].trim());
					couponCategory = couponCategories[j];

//					System.out.println("[BuyCompPage] productCategory[" + productCategory + "] couponCategory[" + couponCategory.trim() + "]");
					if (productCategory.equals(couponCategory.trim())) {
						sellingPrice = sellingPrice * (100 - discount) / 100;
						break;
					}
				}
				if (!CPID.equals("")) {
					bDTO.setCPID(CPID);
				}
			} else {
				System.out.println("CPIDs == null");
			}

			// 구매 내역 추가
			bDTO.setPID(PID);
			bDTO.setbQty(qty);
			bDTO.setPaymentPrice(sellingPrice);
			bDAO.insert(bDTO);

			bDTOs.add(bDTO);
			System.out.println("[BuyCompPageAction] bDTO insert 성공! : " + bDTO);

			// 구매한 상품 장바구니에서 삭제
			CartDTO cDTO = new CartDTO();
			CartDAO cDAO = new CartDAO();

			cDTO.setSearchCondition("장바구니비우기");
			cDTO.setCID(CID);
			System.out.println("[log]BuyCompPageAction_CID [" + CID + "]");
			cDAO.delete(cDTO);
		}

		// coupon 상태 '사용'으로 변경
		if(CPIDs != null) {
			CouponDTO cpDTO = new CouponDTO();
			CouponDAO cpDAO = new CouponDAO();

			cpDTO.setSearchCondition("쿠폰사용");
			for (int i = 0; i < CPIDs.length; i++) {
				System.out.println("[log] CPIDs ["+ CPIDs[i] + "]");

			}
			for (String data : CPIDs) {
				System.out.println("[log] CPIDs [" + data + "");
				cpDTO.setCPID(data);
				cpDAO.update(cpDTO);
				System.out.println("[log] 쿠폰상태변경 [" + cpDAO.update(cpDTO) + "]");

			}
		}

		//request.setAttribute("bDTOs", bDTOs);
		request.setAttribute("orderNum", bDTO.getOrderNum());

		forward.setPath("buyComp.jsp");
		forward.setRedirect(false);
		return forward;
	}

}

package com.naeddoco.nsmwspring.controller.buy;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoDTO;
import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoService;
import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.cartModel.CartService;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoDTO;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoService;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoService;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductService;
import com.naeddoco.nsmwspring.model.subscriptionPolicyModel.SubscriptionPolicyDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class EntrySubscriptionCompPageController {

	@Autowired
	private SubscriptionInfoService subscriptionInfoService;
	@Autowired
	private SubscriptionInfoProductService subscriptionInfoProductService;
	@Autowired
	private CartService cartService;
	@Autowired
	private BuyInfoService buyInfoService;
	@Autowired
	private OrderInfoService orderInfoService;
	
	@RequestMapping(value = "/subscriptionCompPage", method = RequestMethod.POST)
	public String entrySubscriptionCompPageController(SubscriptionInfoDTO subscriptionInfoDTO, SubscriptionInfoProductDTO subscriptionInfoProductDTO, 
													 SubscriptionPolicyDTO subscriptionPolicyDTO , CartDTO cartDTO, BuyInfoDTO buyInfoDTO, OrderInfoDTO orderInfoDTO, 
													 HttpSession session, Model model,
													   @RequestParam("PID[]") List<Integer> productID,
													   @RequestParam("qty[]") List<Integer> productQuantity,
													   @RequestParam("ancSalePrice[]") List<Integer> salePrice,
													   @RequestParam("CID[]") List<Integer> cartID) {
		
		
		/*------------------------------------------------------------------ 구독 내역(마이페이지) 추가 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/

		System.out.println("구독 완료 페이지 요청");
		System.out.println("@RequestParam으로 받은 값");
		System.out.println("상품 PK : " + productID.toString() + "자료형 : " + productID.get(0).getClass());
		System.out.println("상품 수량 : " + productQuantity.toString() + "자료형 : " + productQuantity.get(0).getClass());
		System.out.println("상품 단가 : " + salePrice.toString() + "자료형 : " + salePrice.get(0).getClass());
		System.out.println("카트 번호 : " + cartID.toString() + "자료형 : " + cartID.get(0).getClass());
		
		// 회원 아이디 저장
		String memberID = (String) session.getAttribute("memberID");
		subscriptionInfoDTO.setMemberID(memberID);
		System.out.println("[구독 insert] 회원 ID : " + subscriptionInfoDTO.getMemberID());
		
		// 구독 시작일 저장	
        // 현재 시간을 포함하는 java.util.Date 객체 생성
        Date currentDate = new Date();
        
        // java.util.Date를 java.sql.Timestamp로 변환
        Timestamp beginDate = new Timestamp(currentDate.getTime());   
        subscriptionInfoDTO.setBeginDate(beginDate);
        System.out.println("[구독 insert] 현재 시간: " + subscriptionInfoDTO.getBeginDate());
        
        // 배송지
        System.out.println("[구독 insert] 구독 상품을 배송지 /Command객체/: " + subscriptionInfoDTO.getSubscriptionPostCode() + " // " + subscriptionInfoDTO.getSubscriptionAddress() + " // " + subscriptionInfoDTO.getSubscriptionDetailAddress());
         
        // 구독 기간
        System.out.println("[구독 insert] 구독 기간 : " + subscriptionInfoDTO.getSubscriptionClosingTimes()+"개월");
        
        // DAO 분기 저장
        subscriptionInfoDTO.setSearchCondition("insertSubscriptionData");
        // 구독 정보 저장
        boolean subscriptionInfoResult = subscriptionInfoService.insert(subscriptionInfoDTO);
        
        if(!subscriptionInfoResult) {
        	
        	System.out.println("[구독 insert] 실패");
        	
        	return "redirect:/cart";
        }
        
        System.out.println("[구독 insert] 성공");
        
        /*------------------------------------------------------------ 구독 상세 내역 추가 및 장바구니 제거 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        // 방금 추가된 구독 내역의 PK의 가장 큰 값을 가져온다
        subscriptionInfoDTO.setSearchCondition("insertSubscriptionData");
        int maxSubscriptionNum = subscriptionInfoService.selectOne(subscriptionInfoDTO).getMaxPK();
        
        
        // 구독 상품 정보 추가에 사용
        subscriptionInfoProductDTO.setSubscriptionInfoID(maxSubscriptionNum);
        
        subscriptionInfoProductDTO.setSearchCondition("insertSubscriptionData");
		
        // 구독된 상품 수
        int subscriptionCnt;
        
        for(subscriptionCnt = 0; subscriptionCnt < productID.size(); subscriptionCnt++) {
        	
        	// 상품번호
        	subscriptionInfoProductDTO.setProductID(productID.get(subscriptionCnt));
        	// 상품 갯수
        	subscriptionInfoProductDTO.setQuantity(productQuantity.get(subscriptionCnt));      	
        	// 상품 가격
        	subscriptionInfoProductDTO.setPurchasePrice(salePrice.get(subscriptionCnt));
        	 
        	subscriptionInfoProductService.insert(subscriptionInfoProductDTO);
        	
        	cartDTO.setCartID(cartID.get(subscriptionCnt));
        	
        	cartDTO.setSearchCondition("deleteCart");
        	cartService.delete(cartDTO);
        }
        
        if(subscriptionCnt < 0) {
        	System.out.println("[구독 상품 insert] 실패");
        	
        	return "redirect:/cart";
        }
        
        System.out.println("[구독 성공] 구독 상품 정보 추가 수 : " + subscriptionCnt);  
        
        /*------------------------------------------------------------------ 구매 내역 추가 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        // 회원 ID
        buyInfoDTO.setMemberID(memberID);
        
        // 구독 PK
        buyInfoDTO.setSubscriptionInfoID(maxSubscriptionNum);
        
        buyInfoDTO.setDeliveryPostcode(subscriptionInfoDTO.getSubscriptionPostCode());
        buyInfoDTO.setDeliveryAddress(subscriptionInfoDTO.getSubscriptionAddress());
        buyInfoDTO.setDeliveryDetailAddress(subscriptionInfoDTO.getSubscriptionDetailAddress());
        
        buyInfoDTO.setSearchCondition("insertSubscriptionData");
        boolean buyInfoResult = buyInfoService.insert(buyInfoDTO);
        
        if(!buyInfoResult) {
        	
        	System.out.println("[구매내역 insert] 추가 실패");
        	
        	return "redirect:/cart";
        }
        
        System.out.println("[구매내역 insert] 추가 성공");
                     
        
        /*------------------------------------------------------------------ 구매 상세 추가 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        // 구매 내역의 MAX PK값
        buyInfoDTO.setSearchCondition("insertSubscriptionData");        
        int maxBuyNum = buyInfoService.selectOne(buyInfoDTO).getMaxPk();
        System.out.println("구매 내역에서 MAX PK : " + maxBuyNum);
        
        orderInfoDTO.setBuyInfoID(maxBuyNum);
        
        orderInfoDTO.setSearchCondition("selectSubscriptionDatas");
        
        // 구매 상품 수
        int orderCnt;        
        for(orderCnt = 0; orderCnt < productID.size(); orderCnt++) {
        	
        	// 상품 PK
        	orderInfoDTO.setProductID(productID.get(orderCnt));
        	
        	// 주문 수량
        	orderInfoDTO.setBuyQuantity(productQuantity.get(orderCnt));
        	
        	// 지불 가격
        	orderInfoDTO.setPaymentPrice(productQuantity.get(orderCnt) * salePrice.get(orderCnt));
        	System.out.println("구매 수량 : " + orderInfoDTO.getBuyQuantity());
        	System.out.println("상품 단가 : " + salePrice.get(orderCnt));
        	System.out.println("지불 가격 : " + orderInfoDTO.getPaymentPrice());
        	
        	orderInfoService.insert(orderInfoDTO);
        }
        
        if(orderCnt < 0) {
        	System.out.println("[구매 상품 insert] 실패");
        	
        	return "redirect:/cart";
        }
        
        System.out.println("[구매 상품 insert] 성공 : " + orderCnt + "개");
        

    
		
        model.addAttribute("subscriptionID", maxSubscriptionNum);
        
		return "/user/subscriptionBuyComp";
	}
}

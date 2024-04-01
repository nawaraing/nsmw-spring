package com.naeddoco.nsmwspring.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.cartModel.CartService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;

// 장바구니에 상품을 추가하는 컨트롤러

@Controller
public class InsertCartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/insertCart", method = RequestMethod.POST)
	public @ResponseBody String insertCart(CartDTO cartDTO, ProductDTO productDTO, Model model, HttpSession session, 
			 								@RequestParam("productID") int productID,
			 								@RequestParam("stock") int productQuantity) {	
		
		System.out.println("[log] 메인페이지 InsertCart 진입");
		
		
		// 세션에 있는 사용자 아이디 저장
		String memberID = (String) session.getAttribute("memberID");
		
		// 로그인한 사용자 받아오기 (필수)
		System.out.println("[log] InsertCart 회원아이디 : " + memberID);
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (memberID == null) {
			
			System.out.println("[log] InsertCart 로그아웃상태");
			
			return "true";
		}				
		
		// 상품의 재고와 수량을 비교
		productDTO.setSearchCondition("getProductDetail");
		productDTO.setProductID(productID);
		productDTO = productService.selectOne(productDTO);
		
		// 장바구니에 담으려는 상품의 수량 저장
		String productStock = String.valueOf(productDTO.getStock()); 
		
		if(productStock == null || productDTO.getStock() < 1) {

			System.out.println("[log] InsertCart 재고없음");
			
			return "false";
			
		}
		
		// 상품의 재고가 수량보다 적다면 실패
		if (productQuantity > productDTO.getStock()) {
			System.out.println("[log] InsertCart 선택한 상품의 재고 부족으로 실패");
			System.out.print("[log] InsertCart 상품 재고 : " + productDTO.getStock() + " < ");
			System.out.println("[log] InsertCart 선택 수량 : " + productQuantity);
			return "false";
		}
	

		// 로그인한 회원의 장바구니에 해당 상품이 담겨있는지 확인
		cartDTO.setSearchCondition("checkCartProductData");
		cartDTO.setMemberID(memberID);
		cartDTO.setProductID(productID);
		
		// 해당 상품의 cartID를 반환
		cartDTO = cartService.selectOne(cartDTO);
		
		
		// 장바구니에 없는 상품이라면 추가
		if (cartDTO == null) {
			
			System.out.println("[log] InsertCart 장바구니에 없는 상품");
			
			cartDTO = new CartDTO();
			cartDTO.setSearchCondition("insertProductData");
			cartDTO.setMemberID(memberID);
			cartDTO.setProductID(productID);
			cartDTO.setProductQuantity(productQuantity);

			// 장바구니에 상품추가 결과에 따른 값 반환
//			if (cartService.insert(cartDTO)) {
//				return "true";
//			} else {
//				return "false";
//			}
			return String.valueOf(cartService.insert(cartDTO));
			
			// 장바구니에 있는 상품이라면 수량 없데이트
		} else {
			
			System.out.println("[log] InsertCart 장바구니 확인");
			System.out.println("[log] InsertCart [상품번호] " + cartDTO.getProductID());
			System.out.println("[log] InsertCart [담긴수량] " + cartDTO.getProductQuantity());
			
			cartDTO.setSearchCondition("updateProductData");
			cartDTO.setProductQuantity(productQuantity);

			System.out.println("[log] InsertCart 변경될 장바구니 정보 : " + cartDTO);
						
//			if (cartService.update(cartDTO)) {
//				return "true";
//			} else {
//				return "false";
//			}
			return String.valueOf(cartService.update(cartDTO)); 
		}

	}

}

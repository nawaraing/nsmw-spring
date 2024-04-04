package com.naeddoco.nsmwspring.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.cartModel.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DeleteCartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/deleteCart", method = RequestMethod.GET)
	public String deleteCart(HttpSession session, 
							 Model model, 
			 				 @RequestParam("cartID") int cartID) {
		
		CartDTO cartDTO = new CartDTO();
		cartDTO.setSearchCondition("deleteCart"); // 쿼리 분기 설정
		cartDTO.setCartID(cartID); // 장바구니 ID set
		
		cartService.delete(cartDTO);
		
		return "redirect:/cart";
		
	}

}

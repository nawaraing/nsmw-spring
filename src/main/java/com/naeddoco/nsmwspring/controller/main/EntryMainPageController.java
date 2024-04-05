package com.naeddoco.nsmwspring.controller.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoDTO;
import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoService;
import com.naeddoco.nsmwspring.model.memberCategoryModel.MemberCategoryDTO;
import com.naeddoco.nsmwspring.model.memberCategoryModel.MemberCategoryService;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoDTO;
import com.naeddoco.nsmwspring.model.orderInfoModel.OrderInfoService;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntryMainPageController {

	@Autowired
	private BuyInfoService buyInfoService;
	@Autowired
	private MemberCategoryService memberCategoryService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private OrderInfoService orderInfoService;

	@GetMapping("/")
	public String entryMain(HttpSession session, Model model) {
		
		//----------------------------------------------- 상단 제품 출력 ↓ -----------------------------------------------
		
		// -----------------------------------------------사용자가 구매한 상품의 PK값을 습득 ↓ -----------------------------------------------
		
		String member = (String) session.getAttribute("memberID"); // 세션에서 사용자 이름 가져오기
				
		BuyInfoDTO buyInfoDTO = new BuyInfoDTO();
		buyInfoDTO.setSearchCondition("getProductIDs"); // 쿼리 분기 설정
		buyInfoDTO.setMemberID(member); // 사용자 이름 set
				
		List<BuyInfoDTO> broughtProductList = buyInfoService.selectAll(buyInfoDTO); // 반환 데이터 저장
		
		// ----------------------------------------------- 메인에서 추천할 상품 정보 습득 ↓ ------------------------------------------------------
		
		if(broughtProductList.size() == 0) { // 구매 내역이 없을 경우
			
			// ----------------------------------------------- 사용자의 건강 정보 습득 ↓ ------------------------------------------------------
			
			MemberCategoryDTO memberCategoryDTO = new MemberCategoryDTO();
			memberCategoryDTO.setSearchCondition("memberCategory"); // 쿼리 분기 설정
			memberCategoryDTO.setMemberID(member); // 사용자 아이디 set
						
			List<MemberCategoryDTO> memberCategoryDTOList = memberCategoryService.selectAll(memberCategoryDTO);
			
			if(memberCategoryDTOList.size() >= 8) { // 건강 정보가 8개 이상인 경우
				
				// ----------------------------------------------- 해당 건강 정보의 상품들 중 가장 많이 팔리는 상품을 하나씩 습득 ↓ ------------------------------------------------------
				
				for(MemberCategoryDTO mc :memberCategoryDTOList) { // 건강 정보 순회
					
					ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(); 
					productCategoryDTO.setSearchCondition("getProductByCategory"); // 쿼리 분기 설정
					productCategoryDTO.setCategoryID(mc.getCategoryID()); // 카테고리 Pk set
					
					List<ProductCategoryDTO> productCategoryDTOList = new ArrayList<>();
					
					productCategoryDTOList.add(productCategoryService.selectOne(productCategoryDTO)); // 반환 값을 리스트에 저장
					
					if(productCategoryDTOList.size() == 8) { // 리스트의 데이터수가 8개가 됬을 경우
						
						break; // for문을 탈출
						
					}
					
					model.addAttribute("recommandProducts", productCategoryDTOList);
					
				}
				
			} else if(memberCategoryDTOList.size() < 8) { // 건강 정보가 8개 미만인 경우
				
				ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(); 
				
				List<ProductCategoryDTO> productCategoryDTOList = new ArrayList<ProductCategoryDTO>();
				
				// ----------------------------------------------- 해당 건강 정보의 상품들 중 가장 많이 팔리는 상품을 하나씩 습득 ↓ ------------------------------------------------------
				
				for(MemberCategoryDTO mc :memberCategoryDTOList) { // 건강 정보 순회
					
					productCategoryDTO.setSearchCondition("getProductByCategory"); // 쿼리 분기 설정
					productCategoryDTO.setCategoryID(mc.getCategoryID()); // 카테고리 Pk set
					
					productCategoryDTOList.add(productCategoryService.selectOne(productCategoryDTO)); // 반환 값을 리스트에 저장
					
				}
				
				// ----------------------------------------------- 부족한 분의 상품 습득 ↓ ------------------------------------------------------
				
				productCategoryDTO.setSearchCondition("getProductByLimit");
				productCategoryDTO.setAncLimit(8 - memberCategoryDTOList.size());
				
				productCategoryDTOList.addAll(productCategoryService.selectAll(productCategoryDTO));
				
				model.addAttribute("recommandProducts", productCategoryDTOList);
				
			}
			
		} else { // 구매 내역이 있을 경우
			
			List<OrderInfoDTO> orderInfoDTOList = new ArrayList<OrderInfoDTO>();
			
			// ----------------------------------------------- 동일한 물품을 구입한 사용자 아이디를 습득 ↓ ------------------------------------------------------
			
			for(BuyInfoDTO bi : broughtProductList) {
			
				OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
				orderInfoDTO.setSearchCondition("getBuyInfoIDByProductID");
				orderInfoDTO.setProductID(bi.getAncProductID());
				orderInfoDTO.setAncMemberID(member);
			
				orderInfoDTOList.addAll(orderInfoService.selectAll(orderInfoDTO));
			
			}
			
			// ----------------------------------------------- 사용자 아이디 중복 데이터 제거 ↓ ------------------------------------------------------
			
			List<OrderInfoDTO> distinctOrderInfoDTOList = new ArrayList<>();
			Set<OrderInfoDTO> set = new HashSet<>();

			for (OrderInfoDTO orderInfoDTO : orderInfoDTOList) {
				
			    if (set.add(orderInfoDTO)) {
			    	
			        distinctOrderInfoDTOList.add(orderInfoDTO);
			        
			    }
			    
			}
			
			// ----------------------------------------------- 다른 사용자가 구매한 물품 습득 ↓ ------------------------------------------------------
		
			List<BuyInfoDTO> otherUserProductList = new ArrayList<BuyInfoDTO>();
			
			for(OrderInfoDTO oi : distinctOrderInfoDTOList) {
				
				buyInfoDTO.setSearchCondition("getNotBuyProduct");
				buyInfoDTO.setMemberID(oi.getAncMemberID());
				
				otherUserProductList.addAll(buyInfoService.selectAll(buyInfoDTO));
				
			}
			
			// ----------------------------------------------- 불필요 상품 삭제 ↓ ------------------------------------------------------
			
			List<BuyInfoDTO> filteredList = new ArrayList<>();
			
			for(BuyInfoDTO a : otherUserProductList) {
				
				int dismatchCount = 0;
			
				for(BuyInfoDTO b : broughtProductList) {
								
					if (a.getAncProductID() != b.getAncProductID()) { 
						
						dismatchCount ++;
										        
					} 
						
				}
				
				if(dismatchCount == broughtProductList.size()) {
					
					filteredList.add(a);
					
				}
				
				if(filteredList.size() == 8) {
					
					break;
					
				}
				
			}
			
			model.addAttribute("recommandProducts", filteredList);
			
		}
		
		//----------------------------------------------- 하단 제품 출력 ↓ -----------------------------------------------
		
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		orderInfoDTO.setSearchCondition("getBestEight");
		
		List<OrderInfoDTO> orderDTOList = orderInfoService.selectAll(orderInfoDTO); 
		
		model.addAttribute("allProducts", orderDTOList); // 메인 하단 상품 리스트 정보
		
		return "user/main"; // main.jsp로 리턴

	}

}
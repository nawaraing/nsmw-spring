package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDAO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateProductController {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductCategoryDAO productCategoryDAO;

	@RequestMapping(value = "/productList/update", method = RequestMethod.GET)
	public String searchAndSortProductList(HttpSession session, 
										  @RequestParam("productID") int productID,
										  @RequestParam("productName") String productName, 
										  @RequestParam("productDetail") String productDetail,
										  @RequestParam("costPrice") int costPrice, 
										  @RequestParam("retailPrice") int retailPrice,
										  @RequestParam("salePrice") int salePrice, 
										  @RequestParam("stock") int stock,
										  @RequestParam("ingredient") String ingredient, 
										  @RequestParam("dosage") String dosage,
										  @RequestParam("expirationDate") String expirationDate,
										  @RequestParam("categoryIDs") List<Integer> categoryIDs) {

		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}

		// -----------------------------------------------상품 정보 갱신 ↓-----------------------------------------------

		ProductDTO productDTO = new ProductDTO();

		productDTO.setSearchCondition("updateAdminProductListData"); // 쿼리 분기 설정
		productDTO.setProductID(productID); // 상품 PK set
		productDTO.setProductName(productName); // 상품 이름 set
		productDTO.setProductDetail(productDetail); // 상품 상세 set
		productDTO.setCostPrice(costPrice); // 상품 원가 set
		productDTO.setRetailPrice(retailPrice); // 상품 소비가 set
 		productDTO.setSalePrice(salePrice); // 상품 판매가 set
		productDTO.setStock(stock); // 상품 재고 set
		productDTO.setIngredient(ingredient); // 상품 성분 set
		productDTO.setDosage(dosage); // 상품 용법 set
		productDTO.setExpirationDate(expirationDate); // 상품 유통기한 set

		productDAO.update(productDTO); // 상품 정보 갱신

		// -----------------------------------------------기존 카테고리 정보 삭제 ↓-----------------------------------------------
			
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
			
		productCategoryDTO.setProductID(productID); // 상품 카테고리 카테고리 ID set
			
		productCategoryDAO.delete(productCategoryDTO); // 카테고리 삭제

		// -----------------------------------------------기존 카테고리 정보 추가-----------------------------------------------

		for(int i = 0; i < categoryIDs.size(); i++) { // 카테고리 아이디 데이터 순회
			
			productCategoryDTO = new ProductCategoryDTO();
			
			productCategoryDTO.setProductID(productID); // 상품 아이디 set
			productCategoryDTO.setCategoryID(categoryIDs.get(i)); // 카테고리 아이디 st
			
			productCategoryDAO.insert(productCategoryDTO); // 카테고리 추가
			
		}
		
		return "productList";

	}

}
package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryService;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;

// 관리자 페이지에서 상품 정보를 변경하는 컨트롤러

@Controller
public class UpdateProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/productList/update", method = RequestMethod.POST)
	public String updateProduct(HttpSession session, 
								@RequestParam("productID") List<Integer> productIDs,
								@RequestParam("productName") List<String> productNames, 
								@RequestParam("productDetail") List<String> productDetails,
								@RequestParam("costPrice") List<Integer> costPrices, 
								@RequestParam("retailPrice") List<Integer> retailPrices,
								@RequestParam("salePrice") List<Integer> salePrices, 
								@RequestParam("stock") List<Integer> stocks,
								@RequestParam("ingredient") List<String> ingredients, 
								@RequestParam("dosage") List<String> dosages,
								@RequestParam("expirationDate") List<String> expirationDates,
								@RequestParam("categoryNames") List<String> categoryNames) {

		// -----------------------------------------------세션 확인 ↓ -----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}

		// -----------------------------------------------상품 정보 갱신 ↓ -----------------------------------------------
		
		for(int i = 0; i < productIDs.size(); i++) {
			
			ProductDTO productDTO = new ProductDTO();
			productDTO.setSearchCondition("updateAdminProductListData"); // 쿼리 분기 설정
			productDTO.setProductID(productIDs.get(i)); // 상품 PK set
			productDTO.setProductName(productNames.get(i)); // 상품 이름 set
			productDTO.setProductDetail(productDetails.get(i)); // 상품 상세 set
			productDTO.setCostPrice(costPrices.get(i)); // 상품 원가 set
			productDTO.setRetailPrice(retailPrices.get(i)); // 상품 소비가 set
	 		productDTO.setSalePrice(salePrices.get(i)); // 상품 판매가 set
			productDTO.setStock(stocks.get(i)); // 상품 재고 set
			productDTO.setIngredient(ingredients.get(i)); // 상품 성분 set
			productDTO.setDosage(dosages.get(i)); // 상품 용법 set
			productDTO.setExpirationDate(expirationDates.get(i)); // 상품 유통기한 set

			productService.update(productDTO); // 상품 정보 갱신
			
		}

		// -----------------------------------------------기존 카테고리 정보 삭제 ↓ -----------------------------------------------
		
		for(int i = 0; i < productIDs.size(); i++) {
			
			System.out.println(productIDs.get(i));
			
			ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
			productCategoryDTO.setSearchCondition("updateAdminProductListData");
			productCategoryDTO.setProductID(productIDs.get(i)); // 상품 카테고리 카테고리 ID set
				
			productCategoryService.delete(productCategoryDTO); // 카테고리 삭제
			
		}

		// -----------------------------------------------새로운 카테고리 정보 추가 ↓ -----------------------------------------------
			
		for(int i = 0; i < categoryNames.size(); i++) { // 카테고리 이름 데이터 순회
			
			// -----------------------------------------------;로 붙어있는 카테고리 이름 분리 ↓ -----------------------------------------------
				
			String[] splitedCategoryNames = categoryNames.get(i).split(";");
			
			for(String categoryName : splitedCategoryNames) {
				
				// -----------------------------------------------이름으로 아이디 값 가져오가 ↓ -----------------------------------------------
				
				CategoryDTO categoryDTO = new CategoryDTO();
				categoryDTO.setCategoryName(categoryName); // DTO에 이름값 set
				
				categoryDTO = categoryService.selectOne(categoryDTO); // 이름 값에 대한 정보 습득
				
				// -----------------------------------------------새로운 카테고리 저장 ↓ -----------------------------------------------
				
				ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
				productCategoryDTO.setSearchCondition("insertProductByAdmin");
				productCategoryDTO.setProductID(productIDs.get(i));
				productCategoryDTO.setCategoryID(categoryDTO.getCategoryID());
				
				productCategoryService.insert(productCategoryDTO);
				
			}
			
		}

		return "redirect:/productList";

	}

}
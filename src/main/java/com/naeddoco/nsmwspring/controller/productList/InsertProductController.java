package com.naeddoco.nsmwspring.controller.productList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.imageModel.ImageDAO;
import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDAO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDAO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import jakarta.servlet.http.HttpSession;

// 관리자 페이지에서 상품을 추가하는 컨트롤러

@Controller
public class InsertProductController {
	
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private ProductImageDAO productImageDAO;
	@Autowired
	private ProductCategoryDAO productCategoryDAO;

	@RequestMapping(value = "/productList/insert", method = RequestMethod.GET)
	public String insertProduct(HttpSession session, 
								@RequestParam("productName") String productName, 
								@RequestParam("productDetail") String productDetail,
								@RequestParam("costPrice") int costPrice,
								@RequestParam("retailPrice") int retailPrice,
								@RequestParam("salePrice") int salePrice,
								@RequestParam("stock") int stock,
								@RequestParam("ingredient") String ingredient,
								@RequestParam("dosage") String dosage,
								@RequestParam("expirationDate") String expirationDate,
								@RequestParam("imagePaths") List<String> imagePaths,
								@RequestParam("categoryIDs") List<Integer> categoryIDs) {
		
		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		// -----------------------------------------------상품 테이블에 데이터 추가 ↓-----------------------------------------------
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setSearchCondition("insertProductByAdmin"); // 쿼리 분기명 set
		productDTO.setProductName(productName); // 상품 이름 set
		productDTO.setProductDetail(productDetail); // 상품 상세 set
		productDTO.setCostPrice(costPrice); // 상품 원가 set
		productDTO.setRetailPrice(retailPrice); // 상품 소비자가 set
		productDTO.setSalePrice(salePrice); // 상품 판매가 set
		productDTO.setStock(stock); // 상품 재고 set
		productDTO.setIngredient(ingredient); // 상품 성분 set
		productDTO.setDosage(dosage); // 상품 용법 set
		productDTO.setExpirationDate(expirationDate); // 상품 소비기한 set
		
		productDAO.insert(productDTO);
		
		// -----------------------------------------------최근 추가된 상품의 PK값 습득 ↓-----------------------------------------------
		
		productDTO.setSearchCondition("getLastOne");
		
		List<ProductDTO> productDTOList = productDAO.selectAll(productDTO); // 가장 최근에 추가된 데이터 select
		
		productDTO = productDTOList.get(0); // DTO에 저장
	
		int productPK = productDTO.getProductID(); // PK값 습득
		
		// -----------------------------------------------상품 이미지 테이블에 데이터 추가 ↓-----------------------------------------------
		
		for(int i=0; i<imagePaths.size(); i++) {
			
			ImageDTO imageDTO = new ImageDTO();
			
			imageDTO.setImagePath(imagePaths.get(i)); // DTO에 이미지 경로 데이터를 set
			
			imageDAO.insert(imageDTO); // 경로 데이터 insert
			
			List<ImageDTO> imageDTOList = imageDAO.selectAll(imageDTO); // 가장 최근에 추가된 데이터 select
			
			imageDTO = imageDTOList.get(0); // DTO에 저장
			
			int imagePK = imageDTO.getImageID(); // PK값 습득
			
			ProductImageDTO productImageDTO = new ProductImageDTO();
			
			productImageDTO.setProductID(productPK); // 상품 PK값 set
			productImageDTO.setImageID(imagePK); // 이미지 PK값 set
			
			productImageDAO.insert(productImageDTO); // 데이터 inserts
			
		}
		
		// -----------------------------------------------상품 카테고리 테이블에 데이터 추가 ↓-----------------------------------------------
		
		for(int i=0; i<categoryIDs.size(); i++) {
			
			ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
			
			productCategoryDTO.setProductID(productPK); // 상품 PK값 set
			productCategoryDTO.setCategoryID(categoryIDs.get(i)); // 카테고리 PK값 set
			
			productCategoryDAO.insert(productCategoryDTO); // 데이터 inserts
			
		}
		
		return "admin/productList";
		
	}

}
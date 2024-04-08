package com.naeddoco.nsmwspring.controller.productList;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryService;
import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.imageModel.ImageService;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryService;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDTO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 관리자 페이지에서 상품을 추가하는 컨트롤러

@Controller
@Slf4j
public class InsertProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/productList/insert", method = RequestMethod.POST)
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
								@RequestParam("images") MultipartFile[] images,
								@RequestParam("imagePaths") List<String> imagePaths,
								@RequestParam("categoryNames") List<String> categoryNames) {
		
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
		productDTO.setSaleState("SALES");
		
		log.debug("insert res: " + productService.insert(productDTO));
		
		// -----------------------------------------------최근 추가된 상품의 PK값 습득 ↓-----------------------------------------------
		
		productDTO.setSearchCondition("getLastOne");
		
		List<ProductDTO> productDTOList = productService.selectAll(productDTO); // 가장 최근에 추가된 데이터 select
		
		productDTO = productDTOList.get(0); // DTO에 저장
	
		int productPK = productDTO.getProductID(); // PK값 습득
		
		// -----------------------------------------------상품 이미지 테이블에 데이터 추가 ↓-----------------------------------------------
		
		for(int i=0; i<imagePaths.size(); i++) {
			
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setSearchCondition("insertProductByAdmin"); // 쿼리 조건 set
			imageDTO.setImagePath("/resources/productImages/" + imagePaths.get(i)); // DTO에 이미지 경로 데이터를 set
			
			imageService.insert(imageDTO); // 경로 데이터 insert
			
			imageDTO.setSearchCondition("getLastOne");
			
			List<ImageDTO> imageDTOList = imageService.selectAll(imageDTO); // 가장 최근에 추가된 데이터 select
			
			imageDTO = imageDTOList.get(0); // DTO에 저장
			int imagePK = imageDTO.getImageID(); // PK값 습득
			
			ProductImageDTO productImageDTO = new ProductImageDTO();
			
			productImageDTO.setSearchCondition("insertProductByAdmin");
			productImageDTO.setProductID(productPK); // 상품 PK값 set
			productImageDTO.setImageID(imagePK); // 이미지 PK값 set
			productImageDTO.setPriority(i+1);
			
			productImageService.insert(productImageDTO); // 데이터 inserts
			
		}
		
		// -----------------------------------------------상품 카테고리 테이블에 데이터 추가 ↓-----------------------------------------------
		
		for(int i=0; i<categoryNames.size(); i++) {
					
			CategoryDTO categoryDTO = new CategoryDTO(); 
			categoryDTO.setCategoryName(categoryNames.get(i)); // 카테고리 이름 set
			categoryDTO = categoryService.selectOne(categoryDTO); // 이름을 조건으로 데이터를 가져옴
					
			int categoryID = categoryDTO.getCategoryID();
					
			ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
			productCategoryDTO.setSearchCondition("insertProductByAdmin");
			productCategoryDTO.setProductID(productPK); // 상품 PK값 set
			productCategoryDTO.setCategoryID(categoryID); // 카테고리 PK값 set
					
			productCategoryService.insert(productCategoryDTO); // 데이터 inserts
					
		}
		
		// -----------------------------------------------이미지 파일 저장 ↓-----------------------------------------------
			
		for (MultipartFile image : images) {
			
			int i = 0;
				 
			try {
					 
				String uploadDir = "src/main/resources/static/productImages/"; // 파일을 저장할 디렉토리 경로
				
				String fileName = imagePaths.get(i); // 업로드할 파일명 설정

				String filePath = uploadDir + "/" + fileName; // 파일 경로 설정
				
				try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) { // 파일을 서버에 저장
					
					byte[] bytes = image.getBytes();
					
					stream.write(bytes);
					
				}
				
			} catch (IOException e) {
					
				e.printStackTrace();
				
			}
			
			i++;
			
		}
				
		return "redirect:/productList";
		
	}

}
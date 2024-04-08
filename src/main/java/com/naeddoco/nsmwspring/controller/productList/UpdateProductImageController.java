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

import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.imageModel.ImageService;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDTO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageService;

import jakarta.servlet.http.HttpSession;

// 관리자 페이지에서 상품의 이미지를 변경하는 컨트롤러

@Controller
public class UpdateProductImageController {
	
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value = "/productList/imageUpdate", method = RequestMethod.POST)
	public String updateImage(HttpSession session, 
							  @RequestParam("productID") int productID,
							  @RequestParam("images") MultipartFile[] images,
							  @RequestParam("imagePaths") List<String> imagePaths) {
		
		//-----------------------------------------------세션 확인 ↓ -----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		//-----------------------------------------------기존 이미지 관련 데이터 삭제 ↓ -----------------------------------------------
		
		//-----------------------------------------------이미지 아이디 습득 ↓ -----------------------------------------------
			
		ProductImageDTO productImageDTO = new ProductImageDTO();
		productImageDTO.setSearchCondition("getImageID");
		productImageDTO.setProductID(productID);
			
		List<ProductImageDTO> productImageDTOList = productImageService.selectAll(productImageDTO);
			
		//----------------------------------------------- 상품 이미지 테이블 정보 삭제 ↓ -----------------------------------------------
			
		productImageDTO.setSearchCondition("deletOldProductImageDatas");
		productImageDTO.setProductID(productID);
			
		productImageService.delete(productImageDTO);
			
		//-----------------------------------------------이미지 경로 정보 습득 ↓-----------------------------------------------
			
		for(ProductImageDTO p : productImageDTOList) {
				
			int imageID = p.getImageID();
				
			ImageDTO imageDTO = new ImageDTO();	
			imageDTO.setSearchCondition("getImagePath");
			imageDTO.setImageID(imageID);
				
			imageDTO = imageService.selectOne(imageDTO);
				
			String imagePath = imageDTO.getImagePath();
				
			//-----------------------------------------------이미지 경로 정보 삭제 ↓-----------------------------------------------
				
			imageDTO.setSearchCondition("deleteAdminProductImageDatas");
			imageDTO.setImageID(imageID);
			imageService.delete(imageDTO);
				
			//-----------------------------------------------이미지 파일 삭제 ↓-----------------------------------------------
				
			try {
				
				File file = new File(imagePath);
				
				file.delete();
	            
			} catch (Exception e){
				
				return "admin/productList";
		            
			}
				
		}
				
		//-----------------------------------------------새로운 이미지 관련 데이터 추가 ↓-----------------------------------------------
		
		for(int i = 0; i < imagePaths.size(); i++) {
			
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setSearchCondition("insertProductByAdmin"); // 쿼리 조건 set
			imageDTO.setImagePath("/resources/productImages/" + imagePaths.get(i)); // DTO에 이미지 경로 데이터를 set
			
			imageService.insert(imageDTO); // 경로 데이터 insert
			
			imageDTO.setSearchCondition("getLastOne");
			
			List<ImageDTO> imageDTOList = imageService.selectAll(imageDTO); // 가장 최근에 추가된 데이터 select
			
			imageDTO = imageDTOList.get(0); // DTO에 저장
			int imagePK = imageDTO.getImageID(); // PK값 습득
			
			productImageDTO.setSearchCondition("insertProductByAdmin");
			productImageDTO.setProductID(productID); // 상품 PK값 set
			productImageDTO.setImageID(imagePK); // 이미지 PK값 set
			productImageDTO.setPriority(i+1);
			
			productImageService.insert(productImageDTO); // 데이터 inserts
			
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
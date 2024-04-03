package com.naeddoco.nsmwspring.controller.productList;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.imageModel.ImageService;
import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageService;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDTO;

import jakarta.servlet.http.HttpSession;

// 관리자 페이지에서 상품의 이미지를 변경하는 컨트롤러

@Controller
public class UpdateProductImageController {
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private ProductImageService productImageService;
	
	@RequestMapping(value = "/productList/imageUpdate", method = RequestMethod.GET)
	public String updateImage(HttpSession session, @RequestParam("imageID") int imageID) {
		
		//-----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		//-----------------------------------------------이미지의 경로 회득 ↓-----------------------------------------------
		
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setSearchCondition("deleteAdminProductImageDatas"); // 쿼리 분기 설정
		imageDTO.setImageID(imageID); // 이미지 아이디 set
		
		imageDTO = imageService.selectOne(imageDTO); // 이미지 정보를 DTO에 저장
		
		Path imagePath = Paths.get(imageDTO.getImagePath()); // 이미지 경로 습득
		
		//-----------------------------------------------이미지 테이블 관련 데이터 삭제 ↓-----------------------------------------------
		
		imageDTO.setSearchCondition("deleteAdminProductImageDatas"); // 쿼리 분기 설정
		imageDTO.setImageID(imageID); // 이미지 아이디 set
		
		imageService.delete(imageDTO); // 이미지 경로 삭제
		
		//-----------------------------------------------상품 이미지 테이블 관련 데이터 삭제 ↓-----------------------------------------------
		
		ProductImageDTO productImageDTO = new ProductImageDTO();
		
		productImageDTO.setSearchCondition("deleteAdminProductImageDatas");
		productImageDTO.setImageID(imageID);
		
		productImageService.delete(productImageDTO);
		
		//-----------------------------------------------이미지 파일 삭제 ↓-----------------------------------------------
		
		try {   
			
			Files.delete(imagePath);     
			
		} catch (NoSuchFileException e) {   
			
			System.out.println("삭제하려는 파일/디렉토리가 없습니다");    
			
		} catch (DirectoryNotEmptyException e) {         
			
			System.out.println("디렉토리가 비어있지 않습니다");  
			
		} catch (IOException e) {            
			
			e.printStackTrace();       
			
		}
		
		return "admin/productList";
		
	}

}
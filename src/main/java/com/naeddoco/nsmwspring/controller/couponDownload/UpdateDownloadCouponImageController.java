package com.naeddoco.nsmwspring.controller.couponDownload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.imageModel.ImageService;
import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponDTO;
import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponService;


@Controller
public class UpdateDownloadCouponImageController {

	@Autowired
	private ProvisionDownloadCouponService provisionDownloadCouponService;
	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/couponDownload/imageUpdate", method = RequestMethod.POST)
	public String insertDownloadCouponController(@RequestParam("images") MultipartFile images, // 이미지
			@RequestParam("imagePaths") String imagePaths,
			@RequestParam("couponDownloadID") int couponDownloadID,
			@RequestParam("imageID") int imageID) {
		
		// 0. 넘겨받은 파라미터 값
		System.out.println("images : " + images);
		System.out.println("imagePaths : " + imagePaths);
		System.out.println("couponDownloadID : " + couponDownloadID);
		System.out.println("imageID : " + imageID);		

		// 1-1. 기존 이미지 삭제
		
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setSearchCondition("getImagePath");
		imageDTO.setImageID(imageID);
		
		boolean deleteResult = imageService.delete(imageDTO);
		
		if(!deleteResult) {
			
			System.out.println("이미지 테이블 : 이미지path 삭제 실패");
			
			return "admin/couponDownload";
			
		}
		
		System.out.println("이미지 테이블 : 이미지path 삭제 성공");
		
		
		// 1-2. local 이미지 파일 삭제
		
		// 상대경로
		String relativePath = "/src/main/resources/static/couponImages";
		// 절대경로
		String absolutePath = System.getProperty("user.dir") + relativePath;
		
		imageDTO.setSearchCondition("deleteAdminProductImageDatas");
		imageDTO.setImageID(imageID);
		
		imageDTO = imageService.selectOne(imageDTO);
		
		System.out.println("imageDTO : " + imageDTO);
		
		String filepath = imageDTO.getImagePath();
		System.out.println("filepath : " + filepath);
		
		String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
		System.out.println("filename : " + filename);

		
	    Path path = Paths.get(absolutePath + "/" + filename);
	    
	    File file = path.toFile();
	    
	    System.out.println("file객체에 저장된 데이터 : " + file);
	   
	    if (file.exists()) {
	    	
	        if (!file.delete()) {
	        	
	        	System.out.println("local 이미지 : 이미지 삭제 실패");
	            
	        }
	        
	        System.out.println("local 이미지 : 이미지 삭제 성공");
	        
	    } else {
	    	
	    	System.out.println("local 이미지 : 이미지 삭제 실패(기존 이미지 없음)");
	        
	    }
		
		
		// 2. 입력받은 이미지 저장

		imageDTO.setSearchCondition("insertProductByAdmin");

		String filePath = null;

		try {

			File dir = new File(absolutePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 파일 저장
			filePath = absolutePath + "/" + images.getOriginalFilename();
			File dest = new File(filePath);
			images.transferTo(dest);

		} catch (IOException e) {
			System.out.println("Failed to save file: " + e.getMessage());
		}
		// DB에 저장할 Path
		String uploadDir = "/resources/couponImages/";
		imageDTO.setImagePath(uploadDir + images.getOriginalFilename());

		boolean insertResult = imageService.insert(imageDTO);
		
		if(!insertResult) {
			
			System.out.println("이미지 추가 실패");
			
			return "admin/couponDownload";
			
		}
		
		System.out.println("이미지 추가 성공");

		// 3. 해당 쿠폰의 이미지 값 변경(방금 추가된 값)
		
		// 위에서 추가된 이미지 PK
		imageDTO.setSearchCondition("getLastOne");
		imageDTO = imageService.selectAll(imageDTO).get(0);
		
		int maxImageID = imageDTO.getImageID();
		System.out.println("추가된 이미지 PK : " + imageID);
		
		ProvisionDownloadCouponDTO provisionDownloadCouponDTO = new ProvisionDownloadCouponDTO();
		
		provisionDownloadCouponDTO.setSearchCondition("updateAdminCouponDownloadImageID");
		provisionDownloadCouponDTO.setImageID(maxImageID);
		provisionDownloadCouponDTO.setProvisionDownloadCouponID(couponDownloadID);
		
		boolean updateResult = provisionDownloadCouponService.update(provisionDownloadCouponDTO);
		
		if(!updateResult) {
			
			System.out.println("다운로드 쿠폰 이미지 변경 실패");
			
			return "admin/couponDownload";
			
		}
		
		System.out.println("다운로드 쿠폰 이미지 변경 성공");

		return "redirect:/couponDownload";
	}

}

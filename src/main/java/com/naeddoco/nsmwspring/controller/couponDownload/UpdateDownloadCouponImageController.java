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

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
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
		log.debug("images : " + images);
		log.debug("imagePaths : " + imagePaths);
		log.debug("couponDownloadID : " + couponDownloadID);
		log.debug("imageID : " + imageID);
		
		ImageDTO imageDTO = new ImageDTO();
		
		
		// 1-1. local 이미지 파일 삭제
		
		// 상대경로
		String relativePath = "/src/main/resources/static/couponImages";
		// 절대경로
		String absolutePath = System.getProperty("user.dir") + relativePath;
		
		// 이미지ID로 이미지 경로 찾기
		imageDTO.setSearchCondition("deleteAdminProductImageDatas");
		imageDTO.setImageID(imageID);
		
		imageDTO = imageService.selectOne(imageDTO);
		
		if(imageDTO != null) {
			
			// 이미지 경로
			String filepath = imageDTO.getImagePath();
			log.debug("filepath : " + filepath);
			
			// 경로에서 이미지 이름
			String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
			log.debug("filename : " + filename);
			
		    Path path = Paths.get(absolutePath + "/" + filename);
		    
		    File file = path.toFile();
		    
		    log.debug("file객체에 저장된 데이터 : " + file);
		   
		    if (file.exists()) {
		    	
		        if (!file.delete()) {
		        	
		        	log.debug("local 이미지 : 이미지 삭제 실패");
		            
		        }
		        
		        log.debug("local 이미지 : 이미지 삭제 성공");
		        
		    } else {
		    	
		    	log.debug("local 이미지 : 이미지 삭제 실패(기존 이미지 없음)");
		        
		    }
			
		}
		
		// 1-2. 기존 이미지 삭제
		
		imageDTO.setSearchCondition("deleteAdminProductImageDatas");
		imageDTO.setImageID(imageID);
		
		boolean deleteResult = imageService.delete(imageDTO);
		
		if(!deleteResult) {
			
			log.debug("이미지 테이블 : 이미지path 삭제 실패");
			
			return "admin/couponDownload";
			
		}
		
		log.debug("이미지 테이블 : 이미지path 삭제 성공");
		
		
		// 2. 입력받은 이미지 저장

		imageDTO.setSearchCondition("insertProductByAdmin");

		String filePath = null;

		try {

			File dir = new File(absolutePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 파일 저장
			filePath = absolutePath + "/" + imagePaths;
			File dest = new File(filePath);
			images.transferTo(dest);

		} catch (IOException e) {
			
			log.debug("local에 이미지 저장 예외발생 : " + e.getMessage());
			
		}
		// DB에 저장할 Path
		String uploadDir = "/resources/couponImages/";
		imageDTO.setImagePath(uploadDir + imagePaths);

		boolean insertResult = imageService.insert(imageDTO);
		
		if(!insertResult) {
			
			log.debug("이미지 추가 실패");
			
			return "admin/couponDownload";
			
		}
		
		log.debug("이미지 추가 성공");

		// 3. 해당 쿠폰의 이미지 값 변경(방금 추가된 값)
		
		// 위에서 추가된 이미지 PK
		imageDTO.setSearchCondition("getLastOne");
		imageDTO = imageService.selectAll(imageDTO).get(0);
		
		int maxImageID = imageDTO.getImageID();
		
		ProvisionDownloadCouponDTO provisionDownloadCouponDTO = new ProvisionDownloadCouponDTO();
		
		provisionDownloadCouponDTO.setSearchCondition("updateAdminCouponDownloadImageID");
		provisionDownloadCouponDTO.setImageID(maxImageID);
		provisionDownloadCouponDTO.setProvisionDownloadCouponID(couponDownloadID);
		
		boolean updateResult = provisionDownloadCouponService.update(provisionDownloadCouponDTO);
		
		if(!updateResult) {
			
			log.debug("다운로드 쿠폰 이미지 변경 실패");
			
			return "admin/couponDownload";
			
		}
		
		log.debug("다운로드 쿠폰 이미지 변경 성공");

		return "redirect:/couponDownload";
	}

}

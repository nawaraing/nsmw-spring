package com.naeddoco.nsmwspring.controller.couponDownload;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryService;
import com.naeddoco.nsmwspring.model.couponCategoryModel.CouponCategoryDTO;
import com.naeddoco.nsmwspring.model.couponCategoryModel.CouponCategoryService;
import com.naeddoco.nsmwspring.model.couponModel.CouponDTO;
import com.naeddoco.nsmwspring.model.couponModel.CouponService;
import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.imageModel.ImageService;
import com.naeddoco.nsmwspring.model.percentageCouponModel.PercentageCouponDTO;
import com.naeddoco.nsmwspring.model.percentageCouponModel.PercentageCouponService;
import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponDTO;
import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponService;
import com.naeddoco.nsmwspring.model.wonCoupon.WonCouponDTO;
import com.naeddoco.nsmwspring.model.wonCoupon.WonCouponService;

@Controller
public class InsertDownloadCouponController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponCategoryService couponCategoryService;
	@Autowired
	private PercentageCouponService percentageCouponService;
	@Autowired
	private WonCouponService wonCouponService;
	@Autowired
	private ProvisionDownloadCouponService provisionDownloadCouponService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/couponDownload/insert", method = RequestMethod.POST)
	public String insertDownloadCouponController(@RequestParam("images") List<MultipartFile> images, // 이미지
			@RequestParam("productName") String couponName, // 쿠폰 이름
			@RequestParam("distributeDate") String distributeDateStr, // 쿠폰 배포 시작일
			@RequestParam("ancDeployDeadline") String ancDeployDeadlineStr, // 쿠폰 배포 마감일
			@RequestParam("expirationDate") String expirationDateStr, // 쿠폰 만료일
			@RequestParam("categoryNames") List<String> categoryNames, // 카테고리
			@RequestParam("coupon-type") String couponType, // 할인 방식
			@RequestParam("ancDiscount") int ancDiscount, // 할인율
			@RequestParam("ancAmount") int ancAmount // 제한금액

	) {

		CouponDTO couponDTO = new CouponDTO();
		CategoryDTO categoryDTO = new CategoryDTO();
		CouponCategoryDTO couponCategoryDTO = new CouponCategoryDTO();
		ProvisionDownloadCouponDTO provisionDownloadCouponDTO = new ProvisionDownloadCouponDTO();

		/*------------------------------------------------------------------------------- 쿠폰 DAO -----------------------------------------------------------------------------------------------------------*/
		System.out.println("[다운로드 쿠폰Insert] Controller 진입");
		int i = 0;
		for (i = 0; i < images.size(); i++) {
			System.out.println("쿠폰 이미지 : + " + images.get(i));
		}
		System.out.println("선택된 이미지 수 : " + i);
		System.out.println("쿠폰이름 : " + couponName);
		System.out.println("배포일 : " + distributeDateStr);
		System.out.println("마감일 : " + ancDeployDeadlineStr);
		System.out.println("만료일 : " + expirationDateStr);
		for (i = 0; i < categoryNames.size(); i++) {
			System.out.println("카테고리 : " + categoryNames.get(i));
		}
		System.out.println("선택된 카테고리 수 : " + i);
		System.out.println("할인방식 : " + couponType);
		System.out.println("할인율 : " + ancDiscount);
		System.out.println("리미트 : " + ancAmount);

		// HTML에서 String으로 받기때문에 파싱

		// 배포 만료일
		Timestamp ancDeployDeadline = null;
		// 쿠폰 배포일
		Timestamp distributeDate = null;
		// 쿠폰 만료일
		Timestamp expirationDate = null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

			java.util.Date utilDeployDeadline = dateFormat.parse(ancDeployDeadlineStr);
			java.util.Date utildistributeDate = dateFormat.parse(distributeDateStr);
			java.util.Date utilexpirationDate = dateFormat.parse(expirationDateStr);

			ancDeployDeadline = new Timestamp(utilDeployDeadline.getTime());
			distributeDate = new Timestamp(utildistributeDate.getTime());
			expirationDate = new Timestamp(utilexpirationDate.getTime());

		} catch (ParseException e) {
			System.out.println("Timestamp 변환 실패: " + e.getMessage());
		}

		// 쿠폰테이블에 저장
		couponDTO.setSearchCondition("insertAdminCouponData");
		couponDTO.setCouponName(couponName);
		couponDTO.setDistributeDate(distributeDate);
		couponDTO.setExpirationDate(expirationDate);
		couponDTO.setCouponType(couponType);
		boolean couponInsertResult = couponService.insert(couponDTO);

		if (!couponInsertResult) {
			System.out.println("[쿠폰Insert] 실패");

			return "admin/couponDownload";
		}

		System.out.println("[쿠폰Insert] 성공");

		/*------------------------------------------------------------------------------- 쿠폰 카테고리 DAO -----------------------------------------------------------------------------------------------------------*/

		// 위에서 추가된 쿠폰의 PK
		couponDTO.setSearchCondition("selectLastId");
		couponDTO = couponService.selectOne(couponDTO);

		int couponID = couponDTO.getCouponID();
		System.out.println("쿠폰ID : " + couponID);

		List<CategoryDTO> categoryList = categoryService.selectAll(categoryDTO);

		List<Integer> categoryNum = new ArrayList<>();

		// 카테고리 전체를 한번 순회
		for (i = 0; i < categoryList.size(); i++) {

			// 선택된 카테고리를 순회
			for (int j = 0; j < categoryNames.size(); j++) {

				// 선택된 카테고리가 카테고리 목록과 일치하면
				if (categoryList.get(i).getCategoryName().equals(categoryNames.get(j))) {

					categoryNum.add(categoryList.get(i).getCategoryID());

				}
			}
		}

		couponCategoryDTO.setSearchCondition("insertAdminCouponData");
		couponCategoryDTO.setCouponID(couponID);

		boolean couponCategoryResult = false;

		for (i = 0; i < categoryNum.size(); i++) {

			System.out.println("쿠폰 카테고리 추가 categoryNum.get(i) : " + categoryNum.get(i));
			
			couponCategoryDTO.setCategoryID(categoryNum.get(i));

			couponCategoryResult = couponCategoryService.insert(couponCategoryDTO);
		}

		if (!couponCategoryResult) {

			System.out.println("쿠폰카테고리 추가 실패");

			return "admin/couponDownload";

		}

		System.out.println("쿠폰카테고리 추가 성공 [" + categoryNum + "]");

		/*------------------------------------------------------------------------------- 이미지 DAO -----------------------------------------------------------------------------------------------------------*/

		ImageDTO imageDTO = new ImageDTO();
		
		for (i = 0; i < images.size(); i++) {
			
			System.out.println((i+1) + "번 이미지 이름 : " + images.get(i).getOriginalFilename());
			
			imageDTO.setSearchCondition("insertProductByAdmin");

			String filePath = null;

			try {								
				// 상대경로
				String relativePath = "/src/main/resources/static/couponImages";
				// 절대경로
				String absolutePath = System.getProperty("user.dir") + relativePath;
				
				File dir = new File(absolutePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// 파일 저장
				filePath = absolutePath + "/" + images.get(i).getOriginalFilename();
				File dest = new File(filePath);
				images.get(i).transferTo(dest);

			} catch (IOException e) {
				System.out.println("Failed to save file: " + e.getMessage());
			}
			// DB에 저장할 Path
			String uploadDir = "/resources/couponImages";
			imageDTO.setImagePath(uploadDir+images.get(i).getOriginalFilename());
			
			imageService.insert(imageDTO);	
		}

		/*------------------------------------------------------------------------------- 쿠폰 타입 DAO -----------------------------------------------------------------------------------------------------------*/

		boolean typeCouponInsertResult;

		// 쿠폰 타입에 따른 추가
		if (couponType == "PERCENTAGE") { // %쿠폰

			PercentageCouponDTO percentageCouponDTO = new PercentageCouponDTO();

			System.out.println("[다운로드 쿠폰] 쿠폰타입 : " + couponType);
			percentageCouponDTO.setSearchCondition("insertAdminCouponData");
			percentageCouponDTO.setCouponID(couponID);
			percentageCouponDTO.setCouponDiscountRate(ancDiscount);
			percentageCouponDTO.setMaxDiscountAmount(ancAmount);

			typeCouponInsertResult = percentageCouponService.insert(percentageCouponDTO);

		} else {

			WonCouponDTO wonCouponDTO = new WonCouponDTO();

			System.out.println("[다운로드 쿠폰] 쿠폰타입 : " + couponType);

			wonCouponDTO.setSearchCondition("insertAdminCouponData");
			wonCouponDTO.setCouponID(couponID);
			wonCouponDTO.setCouponDiscountAmount(ancDiscount);
			wonCouponDTO.setMinOrderAmount(ancAmount);

			typeCouponInsertResult = wonCouponService.insert(wonCouponDTO);

		}

		if (!typeCouponInsertResult) {

			System.out.println("타입에 따른 쿠폰 추가 실패");

			return "admin/couponDownload";
		}

		System.out.println("타입에 따른 쿠폰 추가 성공");

		provisionDownloadCouponDTO.setSearchCondition("insertAdminCouponGradeData");
		provisionDownloadCouponDTO.setCouponID(couponID);
		// 테스트
		provisionDownloadCouponDTO.setImageID(1);
		provisionDownloadCouponDTO.setDeployDeadline(ancDeployDeadline);
		// 추가했을 때 기본값이 있어야할 듯
		provisionDownloadCouponDTO.setDeployStatus("???");

		boolean downCouponInsert = provisionDownloadCouponService.insert(provisionDownloadCouponDTO);

		if (!downCouponInsert) {

			System.out.println("다운로드 쿠폰 insert 실패");

			return "admin/couponDownload";

		}

		System.out.println("다운로드 쿠폰 insert 성공");

		return "redirect:/couponDownload";
	}

}

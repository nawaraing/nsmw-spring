package com.naeddoco.nsmwspring.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDAO;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.imageModel.ImageDAO;
import com.naeddoco.nsmwspring.model.imageModel.ImageDTO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDAO;
import com.naeddoco.nsmwspring.model.productCategoryModel.ProductCategoryDTO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDAO;
import com.naeddoco.nsmwspring.model.productImageModel.ProductImageDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrawlingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductCategoryDAO productCategoryDAO; 
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private ProductImageDAO productImageDAO;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//crawling();
	}

	public void crawling() {
		String[] products = {
				"milkthistle",
				"omega3",
				"iron-chewable",
				"calcium-magnesium-vitamind",
				"vitaminb", // 5
				"collagen",
				"probiotics",
				"lutein",
				"vitaminc",
				"hyaluronicacid-spirulina", // 10
				"coenzymeq10",
				"press-wellness-shot",
				"mega-propolis-immune-jelly",
				"peace-free",
				"immune-balance", // 15
				"redginseng-octacosanol",
				"jarrow-womens-fem-dophilus-capsules-30ea",
				"jarrow-pantothenic-acid-100-capsules",
				"natrol-biotin-10000mcg-60ea",
				"swim-up", // 20
				"gmpharm-thehemeironfor-woman",
		};

		int PK = 1001;
		String imgPath;

		for (String product : products) {

			imgPath = downloadPhoto("https://pilly.kr/product/", product, PK++);

			scrapPage("https://pilly.kr/product/" + product, PK, imgPath);

		}

	}

	private String downloadPhoto(String url, String productName, int pk) {

		Document doc = null;
		String path = "";


		try {

			doc = Jsoup.connect(url + productName).get();

			Elements elems = doc.select("li.has-image img[src]");
			String src = "";

			for (Element elem : elems) {

				log.trace(elem.attr("src"));

				src = elem.attr("src");

				break;

			}

			String currentDirectory = System.getProperty("user.dir");
			log.debug("현재 작업 디렉토리: " + currentDirectory);

			URL u = new URL(src);
			InputStream is = u.openStream();
			path = "src/main/resources/static/productImages/" + productName + ".jpg";
			// path = "../../../../Users/kangjunhyeon/eclipse-workspace/nutritional-shopping-mall-web/src/main/webapp/img" + productName + ".jpg";
			// path = "img/" + productName + ".jpg";

			FileOutputStream fos = new FileOutputStream(path);
			int b;

			while ((b = is.read()) != -1) {

				fos.write(b);

			}

			fos.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		log.debug(path + " : 작업 완료");

		return "/resources/productImages/" + productName + ".jpg";

	}

	private void scrapPage(String url, int pk, String imgPath) {

		Document doc = null;

		try {

			doc = Jsoup.connect(url).get();

		} catch (IOException e) {

			e.printStackTrace();

		}


		ProductDTO productDTO = new ProductDTO();
		productDTO.setSearchCondition("crawlProduct");

		// pDetail
		Elements elems = doc.select("div.description");
		Iterator<Element> itr = elems.iterator();

		if (itr.hasNext()) {

			productDTO.setProductDetail(itr.next().text());

		}

		// costPrice
		// regularPrice
		// sellingPrice
		elems = doc.select("div.price"); // "13,500원"
		itr = elems.iterator();

		if (itr.hasNext()) {

			String strPrice = itr.next().text();

			if (strPrice.contains("%")) {

				strPrice = strPrice.split(" ")[2];

			}

			strPrice = strPrice.substring(0, strPrice.length() - 1);

			strPrice = strPrice.replace(String.valueOf(','), "");

			log.trace("price: " + strPrice);

			int selling = Integer.parseInt(strPrice);

			productDTO.setCostPrice(selling - 2000);

			productDTO.setRetailPrice(selling - 1000);

			productDTO.setSalePrice(selling);

		}

		// pQty
		productDTO.setStock(30);

		// imagePath
		productDTO.setAncImagePath(imgPath);

		// sellingState
		productDTO.setSaleState("SALES");

		elems = doc.select("dd");

		itr = elems.iterator();

		int cnt = 0;

		while (itr.hasNext()) {

			Element e = itr.next();

			if (cnt == 0) {

				// pName
				String pName = e.text(); // "필리 오메가3"

				String[] strs = pName.split("\\s+");

				if (strs.length > 0) {

					pName = "";
					for (int i = 0; i < strs.length; i++) {
						if (strs[i].equals("필리") || strs[i].equals(":")) continue;
						if (strs[i].contains("캡슐") || strs[i].contains("ml")) break;
						
						pName += strs[i] + " ";
					}

				}

				productDTO.setProductName(pName);

			} else if (cnt == 3) {

				// exp
				String exp = e.text().split(" /")[0];
				productDTO.setExpirationDate(exp);

			} else if (cnt == 6) {

				String str = e.text();

				// usage
				String[] strs = str.split(" 1일 섭취량 당 함량 : ");

				String usage = "";

				strs = strs[0].split("1일 섭취량 : ");

				if (strs.length > 1) {

					usage = strs[1];

				} else {

					usage = "1캡슐";

				}

				productDTO.setDosage(usage);

				// ingredient
				if (strs.length > 1) {

					str = strs[1];

				}

				strs = str.split(" ※ ");

				str = strs[0];

				productDTO.setIngredient(str);

			} else if (cnt == 7) {

				// category
				String str = e.text();
				log.trace("row-7: " + str);

				if (str.contains("눈")) {
					productDTO.setAncCategory("눈");
				} else if (str.contains("간")) {
					productDTO.setAncCategory("간");
				} else if (str.contains("뼈")) {
					productDTO.setAncCategory("뼈/치아");
				} else if (str.contains("에너지")) {
					productDTO.setAncCategory("활력");
				} else if (str.contains("스트레스") || str.contains("면역")) {
					productDTO.setAncCategory("면역");
				} else if (str.contains("기억력")) {
					productDTO.setAncCategory("두뇌");
				} else if (str.contains("피부")) {
					productDTO.setAncCategory("피부");
				} else if (str.contains("유산균")) {
					productDTO.setAncCategory("소화");
				} else if (str.contains("혈압")) {
					productDTO.setAncCategory("고혈압");
				}

			}

			cnt++;
		}

		log.debug(productDTO.toString());

		if (productDAO.insert(productDTO)) {

			log.debug("크롤링 성공!!");

			productDTO.setSearchCondition("getLastOne"); // 검색 조건을 getLastOne으로 설정

			List<ProductDTO> productDTOList = productDAO.selectAll(productDTO); // selectAll 쿼리 실행 후 결과 반환

			for(ProductDTO x : productDTOList) { // 결과 순회

				CategoryDTO categoryDTO = new CategoryDTO();  // 카테고리 DTO 호출

				categoryDTO.setCategoryName(productDTO.getAncCategory()); // 카테고리 DTO에 카테고리 이름 데이터를 set

				categoryDTO = categoryDAO.selectOne(categoryDTO); // 카테고리 DTO를 이용해 특정 데이터를 습득

				log.trace("SELECTONE : " + categoryDTO); // 카테고리 DTO 출력

				ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(); // 상품 카테고리 사이 테이블 DTO 호출

				productCategoryDTO.setSearchCondition("insertProductByAdmin");
				
				productCategoryDTO.setProductID(x.getProductID()); // 상품 카테고리 사이 테이블 DTO에 상품 PK를 set

				productCategoryDTO.setCategoryID(categoryDTO.getCategoryID()); // 상품 카테고리 사이 테이블 DTO에 카테고리 PK를 set

				productCategoryDAO.insert(productCategoryDTO); // 상품 카테고리 사이 테이블에 데이터를 insert
				
				ImageDTO imageDTO = new ImageDTO(); // 이미지 DTO 호출
				
				imageDTO.setSearchCondition("insertProductByAdmin");
				
				imageDTO.setImagePath(productDTO.getAncImagePath()); // 이미지 DTO에 이미지 경로 데이터 set
				
				imageDAO.insert(imageDTO); // 이미지 테이블에 데이터를 insert
				
				imageDTO.setSearchCondition("getLastOne"); // 이미지 DTO 검색 조건 설정
				
				List<ImageDTO> imageDTOList = imageDAO.selectAll(imageDTO); // 이미지 테이블 SELECTALL 실행
				
				for(ImageDTO y : imageDTOList) { // 이미지 테이블 SELECTALL 반환 값 순회	
					
					ProductImageDTO productImageDTO = new ProductImageDTO(); // 상품 이미지 사이 테이블 호출 
					
					productImageDTO.setSearchCondition("insertProductByAdmin");
					 
					productImageDTO.setImageID(y.getImageID()); // 이미지 아이디값 set
					 
					productImageDTO.setProductID(x.getProductID()); // 상품 아이디 값 set
					
					productImageDAO.insert(productImageDTO);
					
				}
				
			}

		} else {

			log.warn("크롤링 실패...");

		}

	}

}

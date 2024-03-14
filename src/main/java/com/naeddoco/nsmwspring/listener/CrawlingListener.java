package com.naeddoco.nsmwspring.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.naeddoco.nsmwspring.model.productModel.ProductDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrawlingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ProductDAO pDAO;
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	crawling();
    }
    
	public void crawling() {
		String[] products = {
				"omega3",
				"milkthistle",
				"iron-chewable",
				"calcium-magnesium-vitamind",
				"vitaminb", // 5
				"collagen",
				"probiotics",
				"lutein",
				"vitaminc",
				"hyaluronicacid-spirulina", // 10
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
//				System.out.println(elem.attr("src"));
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

        log.debug("작업 완료 - " + path);
        return "productImages/" + productName + ".jpg";
		
	}
	
	private void scrapPage(String url, int pk, String imgPath) {

		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ProductDTO pDTO = new ProductDTO();
		pDTO.setSearchCondition("crawlProduct");
		
		// PID
		// DB에서 넣어줌
		// pDTO.setPID(pk);

		// pDetail
		Elements elems = doc.select("div.description");
		Iterator<Element> itr = elems.iterator();
		if (itr.hasNext()) {
			// System.out.println("div.desctiption: " + itr.next().text());
			pDTO.setProductDetail(itr.next().text());
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
//			System.out.println(strPrice);
			int selling = Integer.parseInt(strPrice);
			// int selling = 3000;
			pDTO.setCostPrice(selling + 2000);
			pDTO.setRetailPrice(selling + 1000);
			pDTO.setSalePrice(selling);
		}

		// pQty
		pDTO.setStock(30);

		// regTime : 현재 시간
		// DB에서 넣어줌
		// pDTO.setRegTime(Timestamp.from(Instant.now()));
		
		// imagePath
		pDTO.setAncImagePath(imgPath);
		
		// sellingState
		pDTO.setSaleState("SALES");
		
		elems = doc.select("dd");
		itr = elems.iterator();
		int cnt = 0;
		while (itr.hasNext()) {
			Element e = itr.next();
			
			if (cnt == 0) {

				// pName
				String pName = e.text(); // 필리 오메가3
				String[] strs = pName.split("\\s+");
				if (strs.length > 1) {
					pName = strs[1];
				}
				pDTO.setProductName(pName);

			} else if (cnt == 3) {

				// exp
				String exp = e.text().split(" /")[0];
				pDTO.setExpirationDate(exp);

			} else if (cnt == 6) {

				String str = e.text();

				// dosage
				String[] strs = str.split(" 1일 섭취량 당 함량 : ");
				String dosage = "";
				strs = strs[0].split("1일 섭취량 : ");
				if (strs.length > 1) {
					dosage = strs[1];
				} else {
					dosage = "1캡슐";
				}
				pDTO.setDosage(dosage);

				// ingredient
				if (strs.length > 1) {
					str = strs[1];
				}
				strs = str.split(" ※ ");
				str = strs[0];
				pDTO.setIngredient(str);

			} else if (cnt == 7) {

				// category
				String str = e.text();
				if (str.contains("눈")) {
					pDTO.setAncCategory("눈");
				} else if (str.contains("간")) {
					pDTO.setAncCategory("간");
				} else if (str.contains("뼈")) {
					pDTO.setAncCategory("뼈/치아");
				} else if (str.contains("에너지")) {
					pDTO.setAncCategory("활력");
				} else if (str.contains("스트레스") || str.contains("면역")) {
					pDTO.setAncCategory("면역");
				} else if (str.contains("기억력")) {
					pDTO.setAncCategory("두뇌");
				} else if (str.contains("피부")) {
					pDTO.setAncCategory("피부");
				} else if (str.contains("유산균")) {
					pDTO.setAncCategory("소화");
				}
			}

			cnt++;
		}
		
		log.debug(pDTO.toString());
		
		if (pDAO.insert(pDTO)) {
			log.trace("크롤링 성공!!");
		} else {
			log.trace("크롤링 실패...");
		}
		
	}
	
}

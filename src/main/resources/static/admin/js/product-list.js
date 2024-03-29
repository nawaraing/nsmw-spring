/*
 * Product List
 */

let categoryList = [];

(function () {
	console.log('start product-list.js');
	
	let data;
	curDatas = [];
	
	let hasCategoryList = false;
  
	for (let i = 0; i < $('#table-body tr').length; i++) {
		// product.ancCategory
		let categories = [];
		console.log("$('#product-categories-' + i + ' span').length : " + $('#product-categories-' + i + ' span').length);
		for (let j = 0; j < $('#product-categories-' + i + ' span').length; j++) {
//			console.log("$('#product-' + i + '-category-' + j).html() : " + $('#product-' + i + '-category-' + j).html());
			categories.push($('#product-' + i + '-category-' + j).html());
		}
		data = {
			productName: $('#product-name-' + i).html(),
			productDetail: $('#product-detail-' + i).html(),
			costPrice: parseInt($('#cost-price-' + i).html().replace(/,/g, '')),
			retailPrice: parseInt($('#retail-price-' + i).html().replace(/,/g, '')),
			salePrice: parseInt($('#sale-price-' + i).html().replace(/,/g, '')),
			stock: $('#stock-' + i).html(),
			ingredient: $('#ingredient-' + i).html(),
			dosage: $('#dosage-' + i).html(),
			expirationDate: $('#expiration-date-' + i).html(),
			categories: categories,
			registerDate: $('#register-date-' + i).html(),
			modifyDate: $('#modify-date-' + i).html(),
			saleState: $('#sale-state-' + i).html(),
			imagePath: $('#image-' + i).html()
		};
		
		// categoryList
		if (!hasCategoryList) {
			categoryList = [];
			console.log("$('#checkbox-categories-' + i + ' li').length : " + $('#checkbox-categories-' + i + ' li').length);
			for (let j = 0; j < $('#checkbox-categories-' + i + ' li').length; j++) {
				categoryList.push($('#checkbox-product-' + i + '-category-' + j).val());
				console.log("$('#checkbox-product-' + i + '-category-' + j).val() : " + $('#checkbox-product-' + i + '-category-' + j).val());
			}
			
			hasCategoryList = true;
			console.log('Set categoryList');
		}
//		console.log(data.dailyTotalGrossMargine);
		curDatas.push(data);
	}

	totalPage = calculTotalPage(curDatas.length);
	curPage = 1;
	startPage = 1;
	endPage = 1;

	curFile = "productList.jsp";

	composePage(curDatas, curFile, "");
})();
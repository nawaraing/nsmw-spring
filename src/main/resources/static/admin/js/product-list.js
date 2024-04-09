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
		
		let images = '';
		for (let j = 0; j < $('#image-list-' + i + ' > div').length; j++) {
			if (j !== 0) {
				images += ';';
			}
			images += $('#image-list-' + i + '-image-' + j + ' input').val();
		}
		console.log('images : ' + images);

		data = {
			productID: $('#checkbox-id-' + i).val(),
			productName: $('#product-name-' + i).val(),
			productDetail: $('#product-detail-' + i).val(),
			costPrice: parseInt($('#cost-price-' + i).val()),
			retailPrice: parseInt($('#retail-price-' + i).val()),
			salePrice: parseInt($('#sale-price-' + i).val()),
			stock: parseInt($('#stock-' + i).val()),
			ingredient: $('#ingredient-' + i).val(),
			dosage: $('#dosage-' + i).val(),
			expirationDate: $('#expiration-date-' + i).val(),
			categories: categories,
			registerDate: $('#register-date-' + i).html(),
			modifyDate: $('#modify-date-' + i).html(),
			saleState: $('#sale-state-' + i).html(),
			ancImagePath: images
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
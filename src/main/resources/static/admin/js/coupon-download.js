/*
 * Coupon Download
 */

let categoryList = [];

(function () {
	console.log('start coupon-download.js');
	
	let data;
	curDatas = [];
	
	for (let i = 0; i < $('#table-body tr').length; i++) {
		// product.ancCategory
		let categories = [];
		console.log("$('#coupon-categories-' + i + ' div').length : " + $('#coupon-categories-' + i + ' div').length);
		for (let j = 0; j < $('#coupon-categories-' + i + ' div').length; j++) {
//			console.log("$('#product-' + i + '-category-' + j).html() : " + $('#product-' + i + '-category-' + j).html());
			categories.push($('#coupon-' + i + '-category-' + j).html());
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
			couponDownloadID: $('#checkbox-id-' + i).val(),
			couponName: $('#coupon-name-' + i).html(),
			distributeDate: $('#distribute-date-' + i).html(),
			ancDeployDeadline: $('#deploy-deadline-' + i).val(),
			expirationDate: $('#expiration-date-' + i).html(),
			categories: categories,
			couponType: $('#coupon-type-' + i).html(),
			ancDiscount: $('#coupon-discount-' + i).html(),
			ancAmount: $('#coupon-limit-' + i).html(),
			ancImageID: $('#image-id-' + i).val(),
			ancImagePath: images,
			ancDeployStatus: $('#deploy-status-' + i).html()
		};
		
		curDatas.push(data);
	}

	totalPage = calculTotalPage(curDatas.length);
	curPage = 1;
	startPage = 1;
	endPage = 1;

	curFile = "couponDownload.jsp";

	composePage(curDatas, curFile, "");
})();
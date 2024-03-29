/*
 * Member List
 */

let categoryList = [];

(function () {
	console.log('start member-list.js');
	
	let data;
	curDatas = [];
	
	for (let i = 0; i < $('#table-body tr').length; i++) {
		// product.ancCategory
		let categories = [];
		for (let j = 0; j < $('#member-categories-' + i + ' div').length; j++) {
//			console.log("$('#product-' + i + '-category-' + j).html() : " + $('#product-' + i + '-category-' + j).html());
			categories.push($('#member-' + i + '-category-' + j).html());
		}
		data = {
			memberName: $('#member-name-' + i).html(),
			memberID: $('#member-id-' + i).html(),
			dayOfBirth: $('#day-of-birth-' + i).html(),
			gender: $('#gender-' + i).html(),
			phoneNumber: $('#phone-number-' + i).html(),
			email: $('#email-' + i).html(),
			ancShippingPostCode: $('#postcode-' + i).html(),
			ancShippingAddress: $('#address-' + i).html(),
			ancShippingAddressDetail: $('#address-detail-' + i).html(),
			ancGradeName: $('#grade-' + i).html(),
			ancCategoryName: categories,
		};
		
		curDatas.push(data);
	}

	totalPage = calculTotalPage(curDatas.length);
	curPage = 1;
	startPage = 1;
	endPage = 1;

	curFile = "memberList.jsp";

	composePage(curDatas, curFile, "");
})();
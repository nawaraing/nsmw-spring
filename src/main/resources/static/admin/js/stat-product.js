/*
 * Stat Product
 */

(function () {
	
	let data;
	curDatas = [];
  
	for (let i = 0; i < $('#table-body tr').length; i++) {
		// TODO
		data = {
			productID: $('#product-id-' + i).html(),
			ansProductName: parseInt($('#product-name-' + i).html()),
			dailyTotalGrossMargine: parseInt($('#total-gross-margine-' + i).html()),
			dailyTotalNetProfit: parseInt($('#total-net-profit-' + i).html()),
			dailyTotalQuantity: parseInt($('#total-quantity-' + i).html())
		};
//		console.log(data.dailyTotalGrossMargine);
		curDatas.push(data);
	}

	totalPage = calculTotalPage(curDatas.length);
	curPage = 1;
	startPage = 1;
	endPage = 1;

	curFile = "statProduct.jsp";
	curType = "date";

	composePage(curDatas, curFile, curType);
    console.log("curFile end statProduct.jsp: " + curFile);
})();

// 전체 페이지 수와 현재 페이지 수 갱신
function calculTotalPage(datasLen) {
//    console.log("dataLen : " + dataLen);
    console.log("[log] start calculTotalPage");
    
    let total = Math.floor(datasLen / 10);
    if (datasLen % 10 != 0) {
		total++;
    }
    if (total == 0) {
    	total = 1;
    }

    return total;
}
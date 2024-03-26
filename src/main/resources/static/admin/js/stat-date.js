/*
 * Stat Date
 */

(function () {
	console.log('start stat-date.js');
	
	let data;
	curDatas = [];
  
	for (let i = 0; i < $('#table-body tr').length; i++) {
		// TODO
		data = {
			dailyTotalCalculateDate: $('#total-calculate-date-' + i).html(),
			dailyTotalGrossMargine: parseInt($('#total-gross-margine-' + i).html()),
			dailyTotalNetProfit: parseInt($('#total-net-profit-' + i).html())
		};
//		console.log(data.dailyTotalGrossMargine);
		curDatas.push(data);
	}

	totalPage = calculTotalPage(curDatas.length);
	curPage = 1;
	startPage = 1;
	endPage = 1;

	curFile = "statDate.jsp";
	curType = "date";
  
	composePage(curDatas, "statDate.jsp", curType);
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
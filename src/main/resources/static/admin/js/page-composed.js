/*
 * 화면 구성 요소 채우기
 * - 테이블
 * - 페이지네이션
 */

function composePage(datas, fileName, type) {
	console.log("[log] start composePage");
//	console.log("[log] curDatas start composePage(): " + curDatas);
	
	if (fileName === "statDate.jsp") {
		fillStatDateTable(datas, type);
	} else if (fileName === "statProduct.jsp") {
		// TODO
	} else {
		// TODO: error
		console.log('error: unexpected file name[' + fileName + ']');
	}
//	console.log("[log] curDatas before composePagination(): " + curDatas);
    composePagination();
}

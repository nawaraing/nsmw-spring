/*
 * 화면 구성 요소 채우기
 * - 테이블
 * - 페이지네이션
 */

function composePage(datas, pageName, type) {
	console.log("[log] start composePage");
	
	if (pageName === "statDate.jsp") {
		fillStatDateTable(datas, type);
	} else if (pageName === "statProduct.jsp") {
		// TODO
	} else {
		// TODO: error
	}
	
    composePagination();
}

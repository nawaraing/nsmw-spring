/*
 * 화면 구성 요소 채우기
 * - 테이블
 * - 페이지네이션
 */

function composePage(datas, fileName, type) {
    console.log("curFile start composePage: " + curFile);

//	console.log("[log] curDatas start composePage(): " + curDatas);
	
	if (fileName === "statDate.jsp") {
		fillStatDateTable(datas, type);
	} else if (fileName === "statProduct.jsp") {
		fillStatProductTable(datas, type);
	} else if (fileName === "productList.jsp") {
		fillProductListTable(datas);
	} else if (fileName === "memberList.jsp") {
		fillMemberListTable(datas);
	} else if (fileName === "couponDownload.jsp") {
		fillCouponDownloadTable(datas);
	} else {
		// TODO: error
		console.log('[error] unexpected fileName: ' + fileName);
	}
//	console.log("[log] curDatas before composePagination(): " + curDatas);
    composePagination();
    console.log("curFile end composePage: " + curFile);
}

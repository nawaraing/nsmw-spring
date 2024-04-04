/*
 * Pagination
 * < 1 2 3 4 5 >
 * 
 * - 최대 5개의 페이지 수 출력
 *   - 페이지를 순차로 5개씩 출력
 *   예시: 1 2 3 4 5 >
 *   예시: < 6 7 8 9 10 >
 *
 * - 화살표를 누르면 현재 보이는 끝 페이지 다음 혹은 이전 페이지로 이동
 *   예시: < 6 7 8 9 10 >
 *       좌측 화살표 -> 5 페이지로 이동
 *       우측 화살표 -> 11 페이지로 이동
 *
 * - 현재 보이는 끝 페이지 다음 혹은 이전 페이지가 없는 경우 일부 화살표 미표시
 *   예시: 1 2 3 4 5 > 마지막 페이지 6 이상
 *   예시: < 6 7 8 마지막 페이지 8
 */
 
// Page 버튼 화면 구성
function composePagination() {
    console.log("curFile start composePagination: " + curFile);

	let pagination = $('#pagination');
	pagination.empty();
	
	let showPageNum = 5;
	
	// < button
	if (curPage > showPageNum) {
		pagination.append(getPrevBtn());
	}
	
	// page button
	
	// 화면에 보여질 시작 페이지, 끝 페이지 계산
	startPage = curPage - curPage % showPageNum + 1;
	if (curPage % showPageNum === 0) startPage -= showPageNum;

	endPage = startPage + showPageNum - 1;
	if (endPage > totalPage) {
		endPage = totalPage;
	}
	for (let i = startPage; i <= endPage; i++) {
		pagination.append(getPageBtn(i));
	}
	
	// > button
	let border = totalPage - totalPage % showPageNum;
	if (totalPage % showPageNum === 0) {
		border -= showPageNum;
	}
	if (curPage <= border) {
		pagination.append(getNextBtn());
	}
	
//	console.log("[log] totalPage: " + totalPage);
//	console.log("[log] curPage: " + curPage);
//	console.log("[log] startPage: " + startPage);
//	console.log("[log] endPage: " + endPage);
//	console.log("[log] curDatas end composePagination(): " + curDatas);
    console.log("curFile end composePagination: " + curFile);
}

function getPrevBtn() {
	let list = $('<li class="page-item prev">');
	let a = $('<button class="page-link" onclick="movePrevPage()"></button>');
	let i = $('<i class="tf-icon bx bx-chevron-left"></i>');
	
	a.append(i);
	list.append(a);
	return list;
}
function getNextBtn() {
	let list = $('<li class="page-item next">');
	let a = $('<button class="page-link" onclick="moveNextPage()"></button>');
	let i = $('<i class="tf-icon bx bx-chevron-right"></i>');
	
	a.append(i);
	list.append(a);
	return list;
}
function getPageBtn(page) {
	let list = $('<li class="page-item">');
	if (page === curPage) {
		list = $('<li class="page-item active">');
	}
	
	let a = $('<button class="page-link" onclick="movePage(\'' + page + '\')">' + page + '</button>');
	
	list.append(a);
	return list;
}
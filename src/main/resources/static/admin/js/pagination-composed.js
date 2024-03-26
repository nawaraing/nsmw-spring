/*
 * Pagination
 * < 1 2 3 4 5 >
 * 
 * - 최대 5개의 페이지 수 출력
 *   - 전체 페이지 수가 5 미만인 경우에만 출력되는 페이지 수가 5 미만
 *
 * - 가능하면 현재 페이지가 중앙에 오도록 배치
 *   - 좌측 또는 우측에 2개의 페이지 수를 표시할 수 없는 상황이면 현재 페이지가 중앙이 아닐 수 있음
 *
 * - 화살표를 누르면 현재 보이는 페이지 다음 혹은 이전 페이지로 이동
 *   예시: < 2 3 4 5 6 > 현재 4 페이지
 *       좌측 화살표 -> 1 페이지로 이동
 *       우측 화살표 -> 7 페이지로 이동
 *   - 화살표로 이동할 수 있는 페이지 숫자가 유효하지 않다면 가장 첫 페이지 혹은 마지막 페이지로 이동
 *   예시: < 1 2 3 4 > 현재 3 페이지, 전체 4 페이지
 *       좌측 화살표 -> 1 페이지로 이동
 *       우측 화살표 -> 4 페이지로 이동
 *
 * - 첫 페이지 혹은 마지막 페이지인 경우 일부 화살표 미표시
 *   예시: 1 2 3 4 5 > 현재 1 페이지
 *   예시: < 1 2 3 4 5 현재 5 페이지, 전체 5 페이지
 */
 
// Page 버튼 화면 구성
function composePagination() {
	console.log("[log] start composePagination");
//	console.log("[log] curDatas start composePagination(): " + curDatas);

	let pagination = $('#pagination');
	pagination.empty();
	
	// < button
	if (curPage > 1) {
		pagination.append(getPrevBtn());
	}
	
	// page button
	
	// 화면에 보여질 시작 페이지, 끝 페이지 계산
	startPage = curPage - 2;
	endPage = curPage + 2;
	if (startPage < 1) {
		startPage = 1;
		endPage += 5 - (endPage - startPage + 1);
	}
	if (endPage > totalPage) {
		endPage = totalPage;
		startPage -= 5 - (endPage - startPage + 1);
	}
	if (startPage < 1) {
		startPage = 1;
	}
	
	for (let i = startPage; i <= endPage; i++) {
		pagination.append(getPageBtn(i));
	}
	
	// > button
	if (curPage < totalPage) {
		pagination.append(getNextBtn());
	}
	
//	console.log("[log] totalPage: " + totalPage);
//	console.log("[log] curPage: " + curPage);
//	console.log("[log] startPage: " + startPage);
//	console.log("[log] endPage: " + endPage);
//	console.log("[log] curDatas end composePagination(): " + curDatas);
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
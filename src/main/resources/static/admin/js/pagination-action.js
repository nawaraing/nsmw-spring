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
 
// Page 버튼 동작
function movePage(param) {
//	console.log("param: " + param);
	curPage = parseInt(param);
	composePage(curDatas, curType);
}
function movePrevPage() {
	if (curPage === 1) {
		return;
	}
	if (startPage - 1 > 0) {
		curPage = startPage - 1;
	} else {
		curPage = 1;
	}
	composePage(curDatas, curType);
}
function moveNextPage() {
//	console.log("[log] start moveNextPage");
	if (curPage === totalPage) {
//		console.log("[log] curPage === totalPage");
		return;
	}
	if (endPage + 1 <= totalPage) {
		curPage = endPage + 1;
	} else {
		curPage = totalPage;
	}
//	console.log("[log] curPage : " + curPage);
	composePage(curDatas, curType);
}

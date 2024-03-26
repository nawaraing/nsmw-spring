/*
* 검색 기능
*/

function searchProductDate() {
//    console.log("curFile start searchProductDate: " + curFile);
    let startDate = $('#start-date-input').val(); // 시작일 입력 필드의 값 가져오기
    let endDate = $('#end-date-input').val(); // 종료일 입력 필드의 값 가져오기
    $.ajax({
        type : "GET",
        dataType : "json",
        url : "/statProduct/searchDate",
        data : {
            "startDate" : startDate,
            "endDate" : endDate
        },
        success : function(datas) {
			doSearchSucc(datas, "date");
        }
    });
//    console.log("curFile end searchProductDate: " + curFile);
}
function searchProductMonth() {
//    console.log("curFile start searchProductMonth: " + curFile);
    let startMonth = $('#start-month-input').val(); // 시작일 입력 필드의 값 가져오기
    let endMonth = $('#end-month-input').val(); // 종료일 입력 필드의 값 가져오기
    $.ajax({
        type : "GET",
        dataType : "json",
        url : "/statProduct/searchMonth",
        data : {
            "startMonth" : startMonth,
            "endMonth" : endMonth
        },
        success : function(datas) {
			doSearchSucc(datas, "month");
        }
    });
//    console.log("curFile end searchProductMonth: " + curFile);
}

function doSearchSucc(datas, type) {
	if (datas === null || datas === undefined) {
		// TODO: 입력이 잘못된 상황, 에러 모달로 안내
	}
	
	totalPage = calculTotalPage(datas.length);
	curPage = 1;
	curDatas = datas;
	curFile = "statProduct.jsp";
	curType = type;
//	console.log("curFile: " + curFile);
	composePage(datas, curFile, curType);
}
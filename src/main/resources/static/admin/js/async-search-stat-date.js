/*
* 검색 기능
*/

function searchDate() {
    let startDate = $('#start-date-input').val(); // 시작일 입력 필드의 값 가져오기
    let endDate = $('#end-date-input').val(); // 종료일 입력 필드의 값 가져오기
    $.ajax({
        type : "GET",
        dataType : "json",
        url : "/statDate/searchDate",
        data : {
            "startDate" : startDate,
            "endDate" : endDate
        },
        success : function(datas) {
			doSearchSucc(datas, "date");
        }
    });
}
function searchMonth() {
    let startMonth = $('#start-month-input').val(); // 시작일 입력 필드의 값 가져오기
    let endMonth = $('#end-month-input').val(); // 종료일 입력 필드의 값 가져오기
    $.ajax({
        type : "GET",
        dataType : "json",
        url : "/statDate/searchMonth",
        data : {
            "startMonth" : startMonth,
            "endMonth" : endMonth
        },
        success : function(datas) {
			doSearchSucc(datas, "month");
        }
    });
}

function doSearchSucc(datas, type) {
	if (datas === null || datas === undefined) {
		// TODO: 입력이 잘못된 상황, 에러 모달로 안내
	}
	
	totalPage = calculTotalPage(datas.length);
	curPage = 1;
	curDatas = datas;
	curFile = "statDate.jsp";
	curType = type;
	composePage(datas, curFile, curType);
}
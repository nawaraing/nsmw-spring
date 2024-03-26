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
        	totalPage = calculTotalPage(datas.length);
        	curPage = 1;
        	curDatas = datas;
        	curType = "date";
        	composePage(datas, curFile, curType);
        	// 페이지네이션을 위한 저장
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
        	totalPage = calculTotalPage(datas.length);
        	curPage = 1;
        	curDatas = datas;
        	curType = "month";
        	composePage(datas, curFile, curType);
        	// 페이지네이션을 위한 저장
        }
    });
}

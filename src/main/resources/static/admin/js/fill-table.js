/*
 * Fill Table
 */

function fillStatDateTable(datas, type) {
	console.log('fillStatDateTable');
    // 엘레먼트 불러오기
    let cardHeader = $('#table-card-header');
    let tbody = $('#table-body');
    let tfooter = $('#table-footer');
    // 테이블 내용 비우기
    cardHeader.empty();
    tbody.empty();
    tfooter.empty();

    // Card header
    if (type === "date") {
        cardHeader.append("일별 통계");
    } else if (type == "month"){
        cardHeader.append("월별 통계");
    } else {
    	// TODO: error
    }

    // Table footer
    if (datas.length <= 0) {
        let row = $('<tr>');
        row.append($('<td>').text("표시할 데이터가 없습니다.."));
        tfooter.append(row);
        return ;
    }

    // Table body
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let row = $('<tr>');
            if (type === "date") {
                row.append($('<td>').append($('<i class="fab fa-angular fa-lg me-3">').append($('<strong id="total-calculate-date-' + index + '">').text(data.dailyTotalCalculateDate))));
                row.append($('<td id="total-gross-margine-' + index + '">').text(formatPrice(data.dailyTotalGrossMargine)));
                row.append($('<td id="total-net-profit-' + index + '">').text(formatPrice(data.dailyTotalNetProfit)));
            } else if (type === "month") {
                row.append($('<td>').append($('<i class="fab fa-angular fa-lg me-3">').append($('<strong id="total-calculate-date-' + index + '">').text(data.monthlyTotalCalculateDate.split("-")[0] + "-" + data.monthlyTotalCalculateDate.split("-")[1]))));
                row.append($('<td id="total-gross-margine-' + index + '">').text(formatPrice(data.monthlyTotalGrossMargine)));
                row.append($('<td id="total-net-profit-' + index + '">').text(formatPrice(data.monthlyTotalNetProfit)));
            } else {
                // TODO: error
            }
            tbody.append(row);
        }
    });
}
function fillStatProductTable(datas, type) {
	console.log('fillStatProductTable');
    console.log("curFile start fillStatProductTable: " + curFile);
    // 엘레먼트 불러오기
    let cardHeader = $('#table-card-header');
    let tbody = $('#table-body');
    let tfooter = $('#table-footer');
    // 테이블 내용 비우기
    cardHeader.empty();
    tbody.empty();
    tfooter.empty();

    // Card header
    if (type === "date") {
        cardHeader.append("일별 통계");
    } else if (type == "month"){
        cardHeader.append("월별 통계");
    } else {
    	// TODO: error
    }

    // Table footer
    if (datas.length <= 0) {
        let row = $('<tr>');
        row.append($('<td>').text("표시할 데이터가 없습니다.."));
        tfooter.append(row);
	    console.log("curFile datas.length <= 0: " + curFile);
        return ;
    }

    // Table body
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let row = $('<tr>');
            if (type === "date") {
                row.append($('<td id="product-id-' + index + '">').text(formatPrice(data.productID)));
                row.append($('<td>').append($('<i class="fab fa-angular fa-lg me-3">').append($('<strong id="product-name-' + index + '">').text(data.ancProductName))));
                row.append($('<td id="total-gross-margine-' + index + '">').text(formatPrice(data.dailyTotalGrossMargine)));
                row.append($('<td id="total-net-profit-' + index + '">').text(formatPrice(data.dailyTotalNetProfit)));
                row.append($('<td id="total-quantity-' + index + '">').text(formatPrice(data.dailyTotalQuantity)));
            } else if (type === "month") {
                row.append($('<td id="product-id-' + index + '">').text(formatPrice(data.productID)));
                row.append($('<td>').append($('<i class="fab fa-angular fa-lg me-3">').append($('<strong id="product-name-' + index + '">').text(data.ancProductName))));
                row.append($('<td id="total-gross-margine-' + index + '">').text(formatPrice(data.monthlyTotalGrossMargine)));
                row.append($('<td id="total-net-profit-' + index + '">').text(formatPrice(data.monthlyTotalNetProfit)));
                row.append($('<td id="total-quantity-' + index + '">').text(formatPrice(data.monthlyTotalQuantity)));
            } else {
                // TODO: error
            }
            tbody.append(row);
        }
    });
    
    console.log("curFile end fillStatProductTable: " + curFile);
}
function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
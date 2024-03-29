/*
 * Utils
 */

// 전체 페이지 수와 현재 페이지 수 갱신
function calculTotalPage(datasLen) {
//    console.log("dataLen : " + dataLen);
    console.log("[log] start calculTotalPage");
    
    let total = Math.floor(datasLen / 10);
    if (datasLen % 10 != 0) {
		total++;
    }
    if (total == 0) {
    	total = 1;
    }

    return total;
}

// 가격 표시 포맷팅
function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
/**
 * Dashboard Analytics Injection
 */

'use strict';

import {renderCharts} from '/resources/admin/assets/js/dashboards-analytics.js';

window.onload = function renderDatas() {
    console.log("[log] start prepareDatas");
    let values = [];
    let dates = [];

	for (let i = 0; i < $('#dashboard-daily-sales-stats tr').length; i++) {
//		console.log("[log] i: " + i + " " + $('#daily-total-calculate-date-' + i).html());
//		console.log("[log] i: " + i + " " + $('#daily-total-gross-margine-' + i).html());
//		console.log("[log] i: " + i + " " + $('#daily-total-net-profit-' + i).html());
		values.push(parseFloat($('#daily-total-gross-margine-' + i).html().replace(/,/g, '')));
		dates.push($('#daily-total-calculate-date-' + i).html());
	}
 
    // 최신 데이터부터 우측 정렬
    values.reverse();
    dates.reverse();
    console.log("values: " + values);
    console.log("dates: " + dates);
    renderCharts(values, dates);
}

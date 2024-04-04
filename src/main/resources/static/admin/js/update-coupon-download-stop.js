/*
 * Update Coupon Download Stop
 */

function updateCouponDownloadStop(maxIdx) {
	
	let updateCouponDownloadStopForm = $('#update-coupon-download-stop-form');
	let isChecked;
	for (let i = 0; i < maxIdx; i++) {
		isChecked = $('#checkbox-id-' + i).prop("checked");
        if (!isChecked) {
			continue;
        }

		let couponID = $('#checkbox-id-' + i);
		console.log('couponID: ' + couponID.val());
		updateCouponDownloadStopForm.append(couponID);
		
		let deployDeadline = $('#deploy-deadline-' + i);
		updateCouponDownloadStopForm.append(deployDeadline);
	}
	
}
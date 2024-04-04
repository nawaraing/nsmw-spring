/*
 * Update Download Coupon
 */

function updateDownloadCoupon(maxIdx) {
	
	let isChecked;
	let updateDownloadCouponForm = $('#update-download-coupon-form');
	
	for (let i = 0; i < maxIdx; i++) {
		isChecked = $('#checkbox-id-' + i).prop("checked");
        if (!isChecked) {
			continue;
        }
		
		let couponID = $('#checkbox-id-' + i);
		updateDownloadCouponForm.append(couponID);
		let deployDeadline = $('#deploy-deadline-' + i);
		updateDownloadCouponForm.append(deployDeadline);
	}
}
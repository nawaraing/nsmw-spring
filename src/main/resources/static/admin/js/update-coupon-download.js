/*
 * Update Coupon Download
 */

function updateCouponDownload(maxIdx) {
	
	let isChecked;
	let updatedCouponDownloadForm = $('#update-coupon-download-form');
	
	for (let i = 0; i < maxIdx; i++) {
		isChecked = $('#checkbox-id-' + i).prop("checked");
        if (!isChecked) {
			continue;
        }
		
		let couponID = $('#checkbox-id-' + i);
		updatedCouponDownloadForm.append(couponID);
		let deployDeadline = $('#deploy-deadline-' + i);
		updatedCouponDownloadForm.append(deployDeadline);
	}
}
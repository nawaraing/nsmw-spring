package com.naeddoco.nsmwspring.model.subscriptionPolicyModel;

import lombok.Data;

@Data
public class SubscriptionPolicyDTO {
	
	private int subscriptionPolicyID; // 인조 식별자
	private int lowerLimitMonth;      // 최소 개월
	private int upperLimitMonth;      // 최대 개월
	private int discountRate;         // 할인 비율
	
	@Override
	public String toString() {
		
		return "SubscriptionPolicyDTO [" + 
			   "subscriptionPolicyID = " + subscriptionPolicyID + ", " + 
			   "lowerLimitMonth = " + lowerLimitMonth + ", " +
			   "upperLimitMonth = " + upperLimitMonth + ", " +
			   "ancCategory = " + discountRate +
			   "]";
				
	}

}

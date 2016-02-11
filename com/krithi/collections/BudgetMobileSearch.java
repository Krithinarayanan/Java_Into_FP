package com.krithi.collections;

public class BudgetMobileSearch implements MobileCriteria {

	@Override
	public boolean canIBuy(Mobile mobile, int budget, int stock) {
		return mobile.getCost() < budget;
	}
}

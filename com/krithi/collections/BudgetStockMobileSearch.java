package com.krithi.collections;

public class BudgetStockMobileSearch implements MobileCriteria {

	@Override
	public boolean canIBuy(Mobile mobile, int budget, int stock) {
		return mobile.getCost() < budget && mobile.getStock() > stock;
	}
}

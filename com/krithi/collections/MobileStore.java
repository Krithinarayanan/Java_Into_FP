
package com.krithi.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This is a primer on Java Functional Programming.
 * 
 * This is a mobile store, providing functionality to query mobile objects with
 * search conditions. Analyzes how simple functions can be treated as
 * first-class entities using lambda expressions. Step-by-step traditional java
 * implementations are discussed to appreciate the power of lambda expression
 * 
 * @author krithinarayanan@gmail.com
 *
 */
public class MobileStore {
	List<Mobile> mobiles = new ArrayList<>();

	public MobileStore() {

		/**
		 * Just initializing mobile. These mobile representations should be
		 * treated with academic interest
		 */
		Mobile asus = new Mobile("ZenFone", 12000, 10);
		Mobile lenova = new Mobile("Lenova K Note", 13000, 20);
		Mobile letv = new Mobile("LeTV", 9500, 3);
		mobiles.add(asus);
		mobiles.add(lenova);
		mobiles.add(letv);
	}

	/**
	 * Get me all the mobile within my budget. This method works fine, would
	 * give you what you want !!
	 * 
	 * @param budget
	 * @return
	 */
	public List<Mobile> findByBudget(int budget) {
		List<Mobile> results = new ArrayList<>();
		for (Mobile m : this.mobiles) {
			if (m.getCost() < budget) {
				results.add(m);
			}
		}
		return results;
	}

	/**
	 * The last-piece syndrome: I was cheated by leading chain who gave me the
	 * last-piece So give me within my budget, ensure you have required stock.
	 * 
	 * Bbbbbbbut wait: Do I have to add methods for each conditional queries?
	 * Oh...Jod !!! my function still looks procedural
	 * 
	 * @param budget
	 * @param stock
	 * @return
	 */
	public List<Mobile> findByBudgetAndStock(int budget, int stock) {
		List<Mobile> results = new ArrayList<>();
		for (Mobile m : this.mobiles) {
			if (m.getCost() < budget && m.getStock() > stock) {
				results.add(m);
			}
		}
		return results;
	}

	/**
	 * 'Katty' thought me the power of interfaces. Create an interface and
	 * implement as many strategies of conditions. The condition check will be
	 * done by the criteria implementations. Hurrayyyy: Based on users
	 * requirement, I can pass the required implementation of MobileCriteria
	 * 
	 * @param criteria
	 * @param budget
	 * @param stock
	 * @return
	 */
	public List<Mobile> findByCriteria(MobileCriteria criteria, int budget, int stock) {
		List<Mobile> results = new ArrayList<>();
		for (Mobile m : this.mobiles) {
			if (criteria.canIBuy(m, budget, stock)) {
				results.add(m);
			}
		}
		return results;
	}

	public void funWithPredicate(Predicate<Mobile> criteria, Consumer<Mobile> results) {

		for (Mobile m : this.mobiles) {
			if (criteria.test(m)) {
				results.accept(m);
			}
		}
	}

	public static void main(String args[]) {
		MobileStore ms = new MobileStore();
		List<Mobile> budgetMobiles = ms.findByBudget(12500);
		List<Mobile> budgetInStock = ms.findByBudgetAndStock(14000, 5);

		List<Mobile> budgetByCriteria = ms.findByCriteria(new BudgetMobileSearch(), 12500, -1);

		List<Mobile> budgetStockByCriteria = ms.findByCriteria(new BudgetStockMobileSearch(), 14000, 5);
		/*
		 * Problem solved: Not really - tomorrow I am planning to expand my
		 * store - add more fields to my mobile object. For search I would add
		 * more implementations of MobileCriteria interface. What would I do
		 * now??? Sounds teasing :-(
		 * 
		 */

		/*
		 * Anonymous classes to my rescue - implement the interface with
		 * anonymous implementation This provide flexibility to program my
		 * conditions without having to add more methods or implementations
		 */
		List<Mobile> budgetByInlineCriteria = ms.findByCriteria(new MobileCriteria() {

			@Override
			public boolean canIBuy(Mobile mobile, int budget, int stock) {
				return mobile.getCost() < budget;
			}
		}, 12500, -1);

		List<Mobile> budgetStockByInlineCriteria = ms.findByCriteria(new MobileCriteria() {

			@Override
			public boolean canIBuy(Mobile mobile, int budget, int stock) {
				return mobile.getCost() < budget && mobile.getStock() > stock;
			}
		}, 14000, 5);

		/*
		 * More powerful programmatic approach using lambda expression. The
		 * interface implementation can be replaced with clear syntax unlike
		 * inner class With this I can add expand search capability even when my
		 * Mobile object grows without new methods
		 */
		MobileCriteria budgetCriteria = (mobile, budget, stock) -> mobile.getCost() < budget;
		List<Mobile> budgetLambdaCriteria = ms.findByCriteria(budgetCriteria, 12500, -1);

		MobileCriteria budgetStockCriteria = (mobile, budget, stock) -> mobile.getCost() < budget
				&& mobile.getStock() > stock;
		List<Mobile> budgetStockLambdaCriteria = ms.findByCriteria(budgetStockCriteria, 14000, 5);

		/*
		 * I don't need to define my MobileCriteria - Predicate is functional
		 * interface that can be used as the assignment target for a lambda
		 * expression or method reference The matching consumer objects can be
		 * mapped to its action (to print in below case)
		 */
		System.out.println("Budget Search...");
		Predicate<Mobile> budgetMobilesPredicate = (mobile) -> mobile.getCost() < 12500;
		ms.funWithPredicate(budgetMobilesPredicate, mobile -> System.out.println(mobile));
		System.out.println("Budget and stock Search...");
		Predicate<Mobile> budgetInStockMobilesPredicate = (mobile) -> mobile.getCost() < 14000 && mobile.getStock() > 5;
		ms.funWithPredicate(budgetInStockMobilesPredicate, mobile -> System.out.println(mobile));

		// ANY CHANGES IN MOBILE OBJECT IN FUTURE CAN BE ACCOMODATED BY WRITING
		// A NEW PREDICATE

	}
}

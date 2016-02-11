package com.krithi.collections;

public class Mobile {
	String name;
	int cost;
	int stock;

	public Mobile(String name, int cost, int stock) {
		this.name = name;
		this.cost = cost;
		this.stock = stock;

	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public String toString() {
		return this.name + ":" + this.cost + ":" + this.stock;
	}
}

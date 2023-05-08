package com.NLCS.TreeShop.models;

public enum EColor {
	NONE("NONE"), PINK("PINK"), YELLOW("YELLOW"), PURPLE("PURPLE"), RED("RED"), WHITE("WHITE"), BLUE("BLUE"), GREEN("GREEN"),
	ORANGE("ORANGE");

	private final String name;

	private EColor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

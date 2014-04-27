package com.pokescrape.data;

public class Pokemon {

	private String name;
	private Forme [] forms;

	public Forme[] getForms() {
		return forms;
	}

	public void setForms(Forme[] forms) {
		this.forms = forms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

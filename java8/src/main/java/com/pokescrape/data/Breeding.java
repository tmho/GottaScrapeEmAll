package com.pokescrape.data;

public class Breeding {

	private String [] eggGroups;
	private Double percentMale;
	private Double percentFemale;
	private Integer eggCycles;
	
	public String[] getEggGroups() {
		return eggGroups;
	}
	public void setEggGroups(String[] eggGroups) {
		this.eggGroups = eggGroups;
	}
	public Double getPercentMale() {
		return percentMale;
	}
	public void setPercentMale(Double percentMale) {
		this.percentMale = percentMale;
	}
	public Double getPercentFemale() {
		return percentFemale;
	}
	public void setPercentFemale(Double percentFemale) {
		this.percentFemale = percentFemale;
	}
	public Integer getEggCycles() {
		return eggCycles;
	}
	public void setEggCycles(Integer eggCycles) {
		this.eggCycles = eggCycles;
	}
}

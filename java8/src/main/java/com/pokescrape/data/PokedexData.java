package com.pokescrape.data;

public class PokedexData {
	private int nationalDexNumber;
	
	private String[] types;
	
	private String species;
	
	private Double heightMeters;
	private Double weightKgs;
	
	private String [] abilities;
	private String hiddenAbility;
	
	public String[] getAbilities() {
		return abilities;
	}
	public void setAbilities(String[] abilities) {
		this.abilities = abilities;
	}
	
	public int getNationalDexNumber() {
		return nationalDexNumber;
	}
	public void setNationalDexNumber(int nationalDexNumber) {
		this.nationalDexNumber = nationalDexNumber;
	}
	
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public Double getHeightMeters() {
		return heightMeters;
	}
	public void setHeightMeters(Double heightMeters) {
		this.heightMeters = heightMeters;
	}
	public Double getWeightKgs() {
		return weightKgs;
	}
	public void setWeightKgs(Double weightKgs) {
		this.weightKgs = weightKgs;
	}
	public String getHiddenAbility() {
		return hiddenAbility;
	}
	public void setHiddenAbility(String hiddenAbility) {
		this.hiddenAbility = hiddenAbility;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
}

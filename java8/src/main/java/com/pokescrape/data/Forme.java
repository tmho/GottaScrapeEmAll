package com.pokescrape.data;

public class Forme {
	
	private String name;
	
	private PokedexData pokedexData;
	private Training training;
	private Breeding breeding;
	private BaseStats baseStats;
	private TypeDefenses [] typeDefenses;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PokedexData getPokedexData() {
		return pokedexData;
	}
	public void setPokedexData(PokedexData pokedexData) {
		this.pokedexData = pokedexData;
	}
	public Training getTraining() {
		return training;
	}
	public void setTraining(Training training) {
		this.training = training;
	}
	public Breeding getBreeding() {
		return breeding;
	}
	public void setBreeding(Breeding breeding) {
		this.breeding = breeding;
	}
	public BaseStats getBaseStats() {
		return baseStats;
	}
	public void setBaseStats(BaseStats baseStats) {
		this.baseStats = baseStats;
	}
	public TypeDefenses[] getTypeDefenses() {
		return typeDefenses;
	}
	public void setTypeDefenses(TypeDefenses[] typeDefenses) {
		this.typeDefenses = typeDefenses;
	}
}

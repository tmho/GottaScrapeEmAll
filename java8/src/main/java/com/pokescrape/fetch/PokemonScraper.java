package com.pokescrape.fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.pokescrape.data.BaseStats;
import com.pokescrape.data.Breeding;
import com.pokescrape.data.Forme;
import com.pokescrape.data.PokedexData;
import com.pokescrape.data.Pokemon;
import com.pokescrape.data.Training;
import com.pokescrape.data.TypeDefenses;

@Component
public class PokemonScraper {
	
	@Resource
	private DocumentFetcher documentFetcher;
	
	public Pokemon scrape(String url) throws IOException {
		Pokemon pokemon = new Pokemon();
		
		Document pokemonInfo = documentFetcher.fetch(url);
		
		pokemon.setName(pokemonInfo.select("article h1").text());

		pokemon.setForms(pokemonInfo.select(".tabset-basics > .svtabs-panel-list").get(0).select("> .svtabs-panel")
				.stream().map(e -> extractForm(e)).toArray(Forme[]::new));
		
		//Forme names are trickier to get because they exist in the tabs rather than on the form
		Elements formNames = pokemonInfo.select(".svtabs-tab-list").get(0).select(".svtabs-tab a");
		for(int i = 0; i < pokemon.getForms().length; i++) {
			pokemon.getForms()[i].setName(formNames.get(i).text()); 
		}
		
		return pokemon;
	}
	
	private Forme extractForm(Element formElement) {
		Forme form = new Forme();
		Elements tables = formElement.select(".vitals-table");
		form.setPokedexData(getPokedexData(tables.get(0)));
		form.setTraining(getTraining(tables.get(1)));
		form.setBreeding(getBreeding(tables.get(2)));
		form.setBaseStats(getBaseStats(tables.get(3)));
		form.setTypeDefenses(getTypeDefenses(formElement.select(".type-table")));
		
		Elements typeDefAbilities = formElement.select(".svtabs-tab-list");
		if(typeDefAbilities.size() > 0) {
			Elements typeDefAbilityNames = typeDefAbilities.get(0).select(".svtabs-tab a");
			for(int i = 0; i < form.getTypeDefenses().length; i++) {
				form.getTypeDefenses()[i].setAbility(typeDefAbilityNames.get(i).text().replaceFirst(" ability", "")); 
			}
		}
		
		return form;
	}
	
	private PokedexData getPokedexData(Element pokeDexTable) {
		PokedexData pokedexData = new PokedexData();
		Elements tableData = pokeDexTable.select("td");
		
		pokedexData.setNationalDexNumber(Integer.parseInt(tableData.get(0).text()));
		
		pokedexData.setTypes(tableData.get(1).select("a").stream().map(t -> t.text()).toArray(String[]::new));
		
		pokedexData.setSpecies(tableData.get(2).text());
		
		String height = tableData.get(3).text();
		pokedexData.setHeightMeters(Double.parseDouble(height.substring(height.indexOf("(") + 1, height.indexOf("m"))));
		
		String weight = tableData.get(4).text();
		pokedexData.setWeightKgs(Double.parseDouble(weight.substring(weight.indexOf("(") + 1, weight.indexOf("k"))));
		
		pokedexData.setAbilities(tableData.get(5).select("a").stream().map(a -> a.text()).toArray(String[]::new));
		String hiddenAbility = tableData.get(5).select("small a").text();
		pokedexData.setHiddenAbility(hiddenAbility.isEmpty() ? null : hiddenAbility);
		
		return pokedexData;
	}
	
	private Training getTraining(Element trainingTable) {
		Training training = new Training();
		Elements tableData = trainingTable.select("td");
		
		String evYield = tableData.get(0).text();
		training.setEvYieldHp(getEvYieldFor("HP", evYield));
		training.setEvYieldAtt(getEvYieldFor("Attack", evYield));
		training.setEvYieldDef(getEvYieldFor("Defense", evYield));
		training.setEvYieldSpAtt(getEvYieldFor("Special Attack", evYield));
		training.setEvYieldSpDef(getEvYieldFor("Special Defense", evYield));
		training.setEvYieldSpd(getEvYieldFor("Speed", evYield));
		
		training.setCatchRate(getFirstInt(tableData.get(1).text()));
		
		training.setBaseHappiness(getFirstInt(tableData.get(2).text()));
		
		training.setBaseExp(getFirstInt(tableData.get(3).text()));
		
		training.setGrowthRate(tableData.get(4).text().startsWith("\u2014") ? null : tableData.get(4).text());
		
		return training;
	}
	
	private Breeding getBreeding(Element breedingTable) {
		Breeding breeding = new Breeding();
		Elements tableData = breedingTable.select("td");
		
		breeding.setEggGroups(tableData.get(0).select("a").stream().map(t -> t.text()).toArray(String[]::new));
		
		breeding.setPercentMale(getPercentGender("male", tableData.get(1).text()));
		breeding.setPercentFemale(getPercentGender("female", tableData.get(1).text()));
		
		breeding.setEggCycles(getFirstInt(tableData.get(2).text()));
		
		return breeding;
	}
	
	private BaseStats getBaseStats(Element baseStatsTable) {
		BaseStats baseStats = new BaseStats();
		Elements tableData = baseStatsTable.select("tbody tr td:eq(1)");
		
		baseStats.setHp(Integer.parseInt(tableData.get(0).text()));
		baseStats.setAttack(Integer.parseInt(tableData.get(1).text()));
		baseStats.setDefense(Integer.parseInt(tableData.get(2).text()));
		baseStats.setSpAttack(Integer.parseInt(tableData.get(3).text()));
		baseStats.setSpDefense(Integer.parseInt(tableData.get(4).text()));
		baseStats.setSpeed(Integer.parseInt(tableData.get(5).text()));
		
		return baseStats;
	}
	
	private TypeDefenses [] getTypeDefenses(Elements typeDefensesTable) {
		List<TypeDefenses> typeDefenses = new ArrayList<TypeDefenses>();
		
		for(int i = 0; i < typeDefensesTable.size(); i+=2) {
			Elements tableData = typeDefensesTable.get(i).select(".type-fx-cell");
			TypeDefenses typeDefense = new TypeDefenses();
			
			typeDefense.setNormal(getTypeDefense(tableData.get(0).attr("class")));
			typeDefense.setFire(getTypeDefense(tableData.get(1).attr("class")));
			typeDefense.setWater(getTypeDefense(tableData.get(2).attr("class")));
			typeDefense.setElectric(getTypeDefense(tableData.get(3).attr("class")));
			typeDefense.setGrass(getTypeDefense(tableData.get(4).attr("class")));
			typeDefense.setIce(getTypeDefense(tableData.get(5).attr("class")));
			typeDefense.setFighting(getTypeDefense(tableData.get(6).attr("class")));
			typeDefense.setPoison(getTypeDefense(tableData.get(7).attr("class")));
			typeDefense.setGround(getTypeDefense(tableData.get(8).attr("class")));
			
			tableData = typeDefensesTable.get(i + 1).select(".type-fx-cell");
			
			typeDefense.setFlying(getTypeDefense(tableData.get(0).attr("class")));
			typeDefense.setPsychic(getTypeDefense(tableData.get(1).attr("class")));
			typeDefense.setBug(getTypeDefense(tableData.get(2).attr("class")));
			typeDefense.setRock(getTypeDefense(tableData.get(3).attr("class")));
			typeDefense.setGhost(getTypeDefense(tableData.get(4).attr("class")));
			typeDefense.setDragon(getTypeDefense(tableData.get(5).attr("class")));
			typeDefense.setDark(getTypeDefense(tableData.get(6).attr("class")));
			typeDefense.setSteel(getTypeDefense(tableData.get(7).attr("class")));
			typeDefense.setFairy(getTypeDefense(tableData.get(8).attr("class")));
			typeDefenses.add(typeDefense);
		}
		
		return typeDefenses.toArray(new TypeDefenses[typeDefenses.size()]);
	}
	
	/*---Helper Methods---*/
	private int getEvYieldFor(String ev, String text) {
		Matcher evMatcher = Pattern.compile("(\\d+) " + ev).matcher(text);
		return evMatcher.find() ? Integer.parseInt(evMatcher.group(1)) : 0;
	}
	
	private Integer getFirstInt(String text) {
		Matcher firstIntMatcher = Pattern.compile("^\\d+").matcher(text);
		return firstIntMatcher.find() ? Integer.parseInt(firstIntMatcher.group()) : null;
	}
	
	private Double getPercentGender(String gender, String text) {
		Matcher genderMatcher = Pattern.compile("(\\d*\\.?\\d*)% " + gender).matcher(text);
		return genderMatcher.find() ? Double.parseDouble(genderMatcher.group(1)) : null;
	}
	
	private int getTypeDefense(String cssClass) {
		Matcher typeDefMatcher = Pattern.compile("type-fx-(\\d+)").matcher(cssClass);
		typeDefMatcher.find();
		return Integer.parseInt(typeDefMatcher.group(1));
	}

}

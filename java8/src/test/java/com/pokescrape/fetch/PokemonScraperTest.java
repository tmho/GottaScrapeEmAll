package com.pokescrape.fetch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pokescrape.data.BaseStats;
import com.pokescrape.data.Breeding;
import com.pokescrape.data.Forme;
import com.pokescrape.data.PokedexData;
import com.pokescrape.data.Pokemon;
import com.pokescrape.data.Training;
import com.pokescrape.data.TypeDefenses;

@RunWith(MockitoJUnitRunner.class)  
public class PokemonScraperTest {

	@Mock
	private DocumentFetcher documentFetcher;
	
	@InjectMocks
	private PokemonScraper pokemonScraper;
	
	@Test
	public void testScrapePinsirFormOne() throws Exception {
		Document mockDoc = Jsoup.parse(new File(
				this.getClass().getClassLoader().getResource("pinsir.html").getPath()), "UTF-8");
		when(documentFetcher.fetch(anyString())).thenReturn(mockDoc);
		
		Pokemon pokemon = pokemonScraper.scrape("fakeUrl");
		assertThat(pokemon.getName(), equalTo("Pinsir"));
		
		//Check number of forms
		assertThat(pokemon.getForms().length, equalTo(2));
		
		/*---FORM ONE----*/
		Forme formOne = pokemon.getForms()[0];
		assertThat(formOne.getName(), equalTo("Pinsir"));
		
		//Check PokedexData
		PokedexData pokedexData = formOne.getPokedexData();
		assertThat(pokedexData.getNationalDexNumber(), equalTo(127));
		assertThat(pokedexData.getTypes(), arrayWithSize(1));
		assertThat(pokedexData.getTypes()[0], equalTo("Bug"));
		assertThat(pokedexData.getSpecies(), equalTo("Stag Beetle Pokémon"));
		assertThat(pokedexData.getHeightMeters(), equalTo(1.5));
		assertThat(pokedexData.getWeightKgs(), equalTo(55.0));
		assertThat(pokedexData.getAbilities(), arrayWithSize(3));
		assertThat(pokedexData.getAbilities()[0], equalTo("Hyper Cutter"));
		assertThat(pokedexData.getAbilities()[1], equalTo("Mold Breaker"));
		assertThat(pokedexData.getAbilities()[2], equalTo("Moxie"));
		assertThat(pokedexData.getHiddenAbility(), equalTo("Moxie"));
		
		//Check Training
		Training training = formOne.getTraining();
		assertThat(training.getEvYieldHp(), equalTo(0));
		assertThat(training.getEvYieldAtt(), equalTo(2));
		assertThat(training.getEvYieldDef(), equalTo(0));
		assertThat(training.getEvYieldSpAtt(), equalTo(0));
		assertThat(training.getEvYieldSpDef(), equalTo(0));
		assertThat(training.getEvYieldSpd(), equalTo(0));
		assertThat(training.getCatchRate(), equalTo(45));
		assertThat(training.getBaseHappiness(), equalTo(70));
		assertThat(training.getBaseExp(), equalTo(200));
		assertThat(training.getGrowthRate(), equalTo("Slow"));
		
		//Check Breeding
		Breeding breeding = formOne.getBreeding();
		assertThat(breeding.getEggGroups(), arrayWithSize(1));
		assertThat(breeding.getEggGroups()[0], equalTo("Bug"));
		assertThat(breeding.getPercentMale(), equalTo(50.0));
		assertThat(breeding.getPercentFemale(), equalTo(50.0));
		assertThat(breeding.getEggCycles(), equalTo(26));
		
		//Check Base Statistics
		BaseStats baseStats = formOne.getBaseStats();
		assertThat(baseStats.getHp(), equalTo(65));
		assertThat(baseStats.getAttack(), equalTo(125));
		assertThat(baseStats.getDefense(), equalTo(100));
		assertThat(baseStats.getSpAttack(), equalTo(55));
		assertThat(baseStats.getSpDefense(), equalTo(70));
		assertThat(baseStats.getSpeed(), equalTo(85));
		
		//Check Type Defenses
		TypeDefenses typeDefenses = formOne.getTypeDefenses()[0];
		assertThat(typeDefenses.getAbility(), nullValue());
		
		assertThat(typeDefenses.getNormal(), equalTo(100));
		assertThat(typeDefenses.getFire(), equalTo(200));
		assertThat(typeDefenses.getWater(), equalTo(100));
		assertThat(typeDefenses.getElectric(), equalTo(100));
		assertThat(typeDefenses.getGrass(), equalTo(50));
		assertThat(typeDefenses.getIce(), equalTo(100));
		assertThat(typeDefenses.getFighting(), equalTo(50));
		assertThat(typeDefenses.getPoison(), equalTo(100));
		assertThat(typeDefenses.getGround(), equalTo(50));
		
		assertThat(typeDefenses.getFlying(), equalTo(200));
		assertThat(typeDefenses.getPsychic(), equalTo(100));
		assertThat(typeDefenses.getBug(), equalTo(100));
		assertThat(typeDefenses.getRock(), equalTo(200));
		assertThat(typeDefenses.getGhost(), equalTo(100));
		assertThat(typeDefenses.getDragon(), equalTo(100));
		assertThat(typeDefenses.getDark(), equalTo(100));
		assertThat(typeDefenses.getSteel(), equalTo(100));
		assertThat(typeDefenses.getFairy(), equalTo(100));
	}
	
	@Test //Mega Pinsir
	public void testScrapePinsirFormTwo() throws Exception {
		Document mockDoc = Jsoup.parse(new File(
				this.getClass().getClassLoader().getResource("pinsir.html").getPath()), "UTF-8");
		when(documentFetcher.fetch(anyString())).thenReturn(mockDoc);
		
		Pokemon pokemon = pokemonScraper.scrape("fakeUrl");
		
		//Check number of forms
		assertThat(pokemon.getForms().length, equalTo(2));
		assertThat(pokemon.getName(), equalTo("Pinsir"));
		
		/*---FORM ONE----*/
		Forme formTwo = pokemon.getForms()[1];
		assertThat(formTwo.getName(), equalTo("Mega Pinsir"));
		
		//Check PokedexData
		PokedexData pokedexData = formTwo.getPokedexData();
		assertThat(pokedexData.getNationalDexNumber(), equalTo(127));
		assertThat(pokedexData.getTypes(), arrayWithSize(2));
		assertThat(pokedexData.getTypes()[0], equalTo("Bug"));
		assertThat(pokedexData.getTypes()[1], equalTo("Flying"));
		assertThat(pokedexData.getSpecies(), equalTo("Stag Beetle Pokémon"));
		assertThat(pokedexData.getHeightMeters(), equalTo(1.7));
		assertThat(pokedexData.getWeightKgs(), equalTo(59.0));
		assertThat(pokedexData.getAbilities(), arrayWithSize(1));
		assertThat(pokedexData.getAbilities()[0], equalTo("Aerialate"));
		assertThat(pokedexData.getHiddenAbility(), nullValue());
		
		//Check Training
		Training training = formTwo.getTraining();
		assertThat(training.getEvYieldHp(), equalTo(0));
		assertThat(training.getEvYieldAtt(), equalTo(0));
		assertThat(training.getEvYieldDef(), equalTo(0));
		assertThat(training.getEvYieldSpAtt(), equalTo(0));
		assertThat(training.getEvYieldSpDef(), equalTo(0));
		assertThat(training.getEvYieldSpd(), equalTo(0));
		assertThat(training.getCatchRate(), nullValue());
		assertThat(training.getBaseHappiness(), nullValue());
		assertThat(training.getBaseExp(), nullValue());
		assertThat(training.getGrowthRate(), nullValue());
		
		//Check Breeding
		Breeding breeding = formTwo.getBreeding();
		assertThat(breeding.getEggGroups(), arrayWithSize(0));
		assertThat(breeding.getPercentMale(), nullValue());
		assertThat(breeding.getPercentFemale(), nullValue());
		assertThat(breeding.getEggCycles(), nullValue());
		
		//Check Base Statistics
		BaseStats baseStats = formTwo.getBaseStats();
		assertThat(baseStats.getHp(), equalTo(65));
		assertThat(baseStats.getAttack(), equalTo(155));
		assertThat(baseStats.getDefense(), equalTo(120));
		assertThat(baseStats.getSpAttack(), equalTo(65));
		assertThat(baseStats.getSpDefense(), equalTo(90));
		assertThat(baseStats.getSpeed(), equalTo(105));
		
		//Check Type Defenses
		TypeDefenses typeDefenses = formTwo.getTypeDefenses()[0];
		assertThat(typeDefenses.getAbility(), nullValue());
		
		assertThat(typeDefenses.getNormal(), equalTo(100));
		assertThat(typeDefenses.getFire(), equalTo(200));
		assertThat(typeDefenses.getWater(), equalTo(100));
		assertThat(typeDefenses.getElectric(), equalTo(200));
		assertThat(typeDefenses.getGrass(), equalTo(25));
		assertThat(typeDefenses.getIce(), equalTo(200));
		assertThat(typeDefenses.getFighting(), equalTo(25));
		assertThat(typeDefenses.getPoison(), equalTo(100));
		assertThat(typeDefenses.getGround(), equalTo(0));
		
		assertThat(typeDefenses.getFlying(), equalTo(200));
		assertThat(typeDefenses.getPsychic(), equalTo(100));
		assertThat(typeDefenses.getBug(), equalTo(50));
		assertThat(typeDefenses.getRock(), equalTo(400));
		assertThat(typeDefenses.getGhost(), equalTo(100));
		assertThat(typeDefenses.getDragon(), equalTo(100));
		assertThat(typeDefenses.getDark(), equalTo(100));
		assertThat(typeDefenses.getSteel(), equalTo(100));
		assertThat(typeDefenses.getFairy(), equalTo(100));
	}
	
	@Test
	public void testButterfree() throws Exception {
		Document mockDoc = Jsoup.parse(new File(
				this.getClass().getClassLoader().getResource("butterfree.html").getPath()), "UTF-8");
		when(documentFetcher.fetch(anyString())).thenReturn(mockDoc);
		
		Pokemon pokemon = pokemonScraper.scrape("fakeUrl");
		assertThat(pokemon.getName(), equalTo("Butterfree"));
		
		//Check number of forms
		assertThat(pokemon.getForms().length, equalTo(1));
		
		Forme form = pokemon.getForms()[0];
		assertThat(form.getName(), equalTo("Butterfree"));
		
		//Check PokedexData
		PokedexData pokedexData = form.getPokedexData();
		assertThat(pokedexData.getNationalDexNumber(), equalTo(12));
		assertThat(pokedexData.getTypes(), arrayWithSize(2));
		assertThat(pokedexData.getTypes()[0], equalTo("Bug"));
		assertThat(pokedexData.getTypes()[1], equalTo("Flying"));
		assertThat(pokedexData.getSpecies(), equalTo("Butterfly Pokémon"));
		assertThat(pokedexData.getHeightMeters(), equalTo(1.09));
		assertThat(pokedexData.getWeightKgs(), equalTo(32.0));
		assertThat(pokedexData.getAbilities(), arrayWithSize(2));
		assertThat(pokedexData.getAbilities()[0], equalTo("Compound Eyes"));
		assertThat(pokedexData.getAbilities()[1], equalTo("Tinted Lens"));
		assertThat(pokedexData.getHiddenAbility(), equalTo("Tinted Lens"));
		
		//Check Training
		Training training = form.getTraining();
		assertThat(training.getEvYieldHp(), equalTo(0));
		assertThat(training.getEvYieldAtt(), equalTo(0));
		assertThat(training.getEvYieldDef(), equalTo(0));
		assertThat(training.getEvYieldSpAtt(), equalTo(2));
		assertThat(training.getEvYieldSpDef(), equalTo(1));
		assertThat(training.getEvYieldSpd(), equalTo(0));
		assertThat(training.getCatchRate(), equalTo(45));
		assertThat(training.getBaseHappiness(), equalTo(70));
		assertThat(training.getBaseExp(), equalTo(160));
		assertThat(training.getGrowthRate(), equalTo("Medium Fast"));
		
		//Check Breeding
		Breeding breeding = form.getBreeding();
		assertThat(breeding.getEggGroups(), arrayWithSize(1));
		assertThat(breeding.getEggGroups()[0], equalTo("Bug"));
		assertThat(breeding.getPercentMale(), equalTo(50.0));
		assertThat(breeding.getPercentFemale(), equalTo(50.0));
		assertThat(breeding.getEggCycles(), equalTo(16));
		
		//Check Base Statistics
		BaseStats baseStats = form.getBaseStats();
		assertThat(baseStats.getHp(), equalTo(60));
		assertThat(baseStats.getAttack(), equalTo(45));
		assertThat(baseStats.getDefense(), equalTo(50));
		assertThat(baseStats.getSpAttack(), equalTo(80));
		assertThat(baseStats.getSpDefense(), equalTo(80));
		assertThat(baseStats.getSpeed(), equalTo(70));
		
		//Check Type Defenses
		TypeDefenses typeDefenses = form.getTypeDefenses()[0];
		assertThat(typeDefenses.getAbility(), nullValue());
		
		assertThat(typeDefenses.getNormal(), equalTo(100));
		assertThat(typeDefenses.getFire(), equalTo(200));
		assertThat(typeDefenses.getWater(), equalTo(100));
		assertThat(typeDefenses.getElectric(), equalTo(200));
		assertThat(typeDefenses.getGrass(), equalTo(25));
		assertThat(typeDefenses.getIce(), equalTo(200));
		assertThat(typeDefenses.getFighting(), equalTo(25));
		assertThat(typeDefenses.getPoison(), equalTo(100));
		assertThat(typeDefenses.getGround(), equalTo(0));
		
		assertThat(typeDefenses.getFlying(), equalTo(200));
		assertThat(typeDefenses.getPsychic(), equalTo(100));
		assertThat(typeDefenses.getBug(), equalTo(50));
		assertThat(typeDefenses.getRock(), equalTo(400));
		assertThat(typeDefenses.getGhost(), equalTo(100));
		assertThat(typeDefenses.getDragon(), equalTo(100));
		assertThat(typeDefenses.getDark(), equalTo(100));
		assertThat(typeDefenses.getSteel(), equalTo(100));
		assertThat(typeDefenses.getFairy(), equalTo(100));
	}
	
	@Test
	public void testDeoxys() throws Exception {
		Document mockDoc = Jsoup.parse(new File(
				this.getClass().getClassLoader().getResource("deoxys.html").getPath()), "UTF-8");
		when(documentFetcher.fetch(anyString())).thenReturn(mockDoc);
		
		Pokemon pokemon = pokemonScraper.scrape("fakeUrl");
		assertThat(pokemon.getName(), equalTo("Deoxys"));
		
		//Check number of forms
		assertThat(pokemon.getForms().length, equalTo(4));
		
		/*---FORM ONE----*/
		Forme form = pokemon.getForms()[0];
		assertThat(form.getName(), equalTo("Normal Forme"));
		
		//Check PokedexData
		PokedexData pokedexData = form.getPokedexData();
		assertThat(pokedexData.getNationalDexNumber(), equalTo(386));
		assertThat(pokedexData.getTypes(), arrayWithSize(1));
		assertThat(pokedexData.getTypes()[0], equalTo("Psychic"));
		assertThat(pokedexData.getSpecies(), equalTo("DNA Pokémon"));
		assertThat(pokedexData.getHeightMeters(), equalTo(1.7));
		assertThat(pokedexData.getWeightKgs(), equalTo(60.8));
		assertThat(pokedexData.getAbilities(), arrayWithSize(1));
		assertThat(pokedexData.getAbilities()[0], equalTo("Pressure"));
		assertThat(pokedexData.getHiddenAbility(), nullValue());
		
		//Check Training
		Training training = form.getTraining();
		assertThat(training.getEvYieldHp(), equalTo(0));
		assertThat(training.getEvYieldAtt(), equalTo(1));
		assertThat(training.getEvYieldDef(), equalTo(0));
		assertThat(training.getEvYieldSpAtt(), equalTo(1));
		assertThat(training.getEvYieldSpDef(), equalTo(0));
		assertThat(training.getEvYieldSpd(), equalTo(1));
		assertThat(training.getCatchRate(), equalTo(3));
		assertThat(training.getBaseHappiness(), equalTo(0));
		assertThat(training.getBaseExp(), equalTo(215));
		assertThat(training.getGrowthRate(), equalTo("Slow"));
		
		//Check Breeding
		Breeding breeding = form.getBreeding();
		assertThat(breeding.getEggGroups(), arrayWithSize(1));
		assertThat(breeding.getEggGroups()[0], equalTo("Undiscovered"));
		assertThat(breeding.getPercentMale(), nullValue());
		assertThat(breeding.getPercentFemale(), nullValue());
		assertThat(breeding.getEggCycles(), equalTo(121));
		
		//Check Base Statistics
		BaseStats baseStats = form.getBaseStats();
		assertThat(baseStats.getHp(), equalTo(50));
		assertThat(baseStats.getAttack(), equalTo(150));
		assertThat(baseStats.getDefense(), equalTo(50));
		assertThat(baseStats.getSpAttack(), equalTo(150));
		assertThat(baseStats.getSpDefense(), equalTo(50));
		assertThat(baseStats.getSpeed(), equalTo(150));
		
		//Check Type Defenses
		TypeDefenses typeDefenses = form.getTypeDefenses()[0];
		assertThat(typeDefenses.getAbility(), nullValue());
		
		assertThat(typeDefenses.getNormal(), equalTo(100));
		assertThat(typeDefenses.getFire(), equalTo(100));
		assertThat(typeDefenses.getWater(), equalTo(100));
		assertThat(typeDefenses.getElectric(), equalTo(100));
		assertThat(typeDefenses.getGrass(), equalTo(100));
		assertThat(typeDefenses.getIce(), equalTo(100));
		assertThat(typeDefenses.getFighting(), equalTo(50));
		assertThat(typeDefenses.getPoison(), equalTo(100));
		assertThat(typeDefenses.getGround(), equalTo(100));
		
		assertThat(typeDefenses.getFlying(), equalTo(100));
		assertThat(typeDefenses.getPsychic(), equalTo(50));
		assertThat(typeDefenses.getBug(), equalTo(200));
		assertThat(typeDefenses.getRock(), equalTo(100));
		assertThat(typeDefenses.getGhost(), equalTo(200));
		assertThat(typeDefenses.getDragon(), equalTo(100));
		assertThat(typeDefenses.getDark(), equalTo(200));
		assertThat(typeDefenses.getSteel(), equalTo(100));
		assertThat(typeDefenses.getFairy(), equalTo(100));
	}
	
	@Test
	public void testScrapePikachu() throws Exception {
		Document mockDoc = Jsoup.parse(new File(
				this.getClass().getClassLoader().getResource("pikachu.html").getPath()), "UTF-8");
		when(documentFetcher.fetch(anyString())).thenReturn(mockDoc);
		
		Pokemon pokemon = pokemonScraper.scrape("fakeUrl");
		assertThat(pokemon.getName(), equalTo("Pikachu"));
		
		//Check number of forms
		assertThat(pokemon.getForms().length, equalTo(1));
		
		Forme form = pokemon.getForms()[0];
		assertThat(form.getName(), equalTo("Pikachu"));
		
		//Check PokedexData
		PokedexData pokedexData = form.getPokedexData();
		assertThat(pokedexData.getNationalDexNumber(), equalTo(25));
		assertThat(pokedexData.getTypes(), arrayWithSize(1));
		assertThat(pokedexData.getTypes()[0], equalTo("Electric"));
		assertThat(pokedexData.getSpecies(), equalTo("Mouse Pokémon"));
		assertThat(pokedexData.getHeightMeters(), equalTo(0.41));
		assertThat(pokedexData.getWeightKgs(), equalTo(6.0));
		assertThat(pokedexData.getAbilities(), arrayWithSize(2));
		assertThat(pokedexData.getAbilities()[0], equalTo("Static"));
		assertThat(pokedexData.getAbilities()[1], equalTo("Lightning Rod"));
		assertThat(pokedexData.getHiddenAbility(), equalTo("Lightning Rod"));
		
		//Check Training
		Training training = form.getTraining();
		assertThat(training.getEvYieldHp(), equalTo(0));
		assertThat(training.getEvYieldAtt(), equalTo(0));
		assertThat(training.getEvYieldDef(), equalTo(0));
		assertThat(training.getEvYieldSpAtt(), equalTo(0));
		assertThat(training.getEvYieldSpDef(), equalTo(0));
		assertThat(training.getEvYieldSpd(), equalTo(2));
		assertThat(training.getCatchRate(), equalTo(190));
		assertThat(training.getBaseHappiness(), equalTo(70));
		assertThat(training.getBaseExp(), equalTo(82));
		assertThat(training.getGrowthRate(), equalTo("Medium Fast"));
		
		//Check Breeding
		Breeding breeding = form.getBreeding();
		assertThat(breeding.getEggGroups(), arrayWithSize(2));
		assertThat(breeding.getEggGroups()[0], equalTo("Fairy"));
		assertThat(breeding.getEggGroups()[1], equalTo("Field"));
		assertThat(breeding.getPercentMale(), equalTo(50.0));
		assertThat(breeding.getPercentFemale(), equalTo(50.0));
		assertThat(breeding.getEggCycles(), equalTo(11));
		
		//Check Base Statistics
		BaseStats baseStats = form.getBaseStats();
		assertThat(baseStats.getHp(), equalTo(35));
		assertThat(baseStats.getAttack(), equalTo(55));
		assertThat(baseStats.getDefense(), equalTo(30));
		assertThat(baseStats.getSpAttack(), equalTo(50));
		assertThat(baseStats.getSpDefense(), equalTo(40));
		assertThat(baseStats.getSpeed(), equalTo(90));
		
		//Check Type Defenses for Ability 1
		TypeDefenses typeDefenses1 = form.getTypeDefenses()[0];
		assertThat(typeDefenses1.getAbility(), equalTo("Static"));
		
		assertThat(typeDefenses1.getNormal(), equalTo(100));
		assertThat(typeDefenses1.getFire(), equalTo(100));
		assertThat(typeDefenses1.getWater(), equalTo(100));
		assertThat(typeDefenses1.getElectric(), equalTo(50));
		assertThat(typeDefenses1.getGrass(), equalTo(100));
		assertThat(typeDefenses1.getIce(), equalTo(100));
		assertThat(typeDefenses1.getFighting(), equalTo(100));
		assertThat(typeDefenses1.getPoison(), equalTo(100));
		assertThat(typeDefenses1.getGround(), equalTo(200));
		
		assertThat(typeDefenses1.getFlying(), equalTo(50));
		assertThat(typeDefenses1.getPsychic(), equalTo(100));
		assertThat(typeDefenses1.getBug(), equalTo(100));
		assertThat(typeDefenses1.getRock(), equalTo(100));
		assertThat(typeDefenses1.getGhost(), equalTo(100));
		assertThat(typeDefenses1.getDragon(), equalTo(100));
		assertThat(typeDefenses1.getDark(), equalTo(100));
		assertThat(typeDefenses1.getSteel(), equalTo(50));
		assertThat(typeDefenses1.getFairy(), equalTo(100));
		
		//Check Type Defenses for Ability 2
		TypeDefenses typeDefenses2 = form.getTypeDefenses()[1];
		assertThat(typeDefenses2.getAbility(), equalTo("Lightning Rod"));
		
		assertThat(typeDefenses2.getNormal(), equalTo(100));
		assertThat(typeDefenses2.getFire(), equalTo(100));
		assertThat(typeDefenses2.getWater(), equalTo(100));
		assertThat(typeDefenses2.getElectric(), equalTo(0));
		assertThat(typeDefenses2.getGrass(), equalTo(100));
		assertThat(typeDefenses2.getIce(), equalTo(100));
		assertThat(typeDefenses2.getFighting(), equalTo(100));
		assertThat(typeDefenses2.getPoison(), equalTo(100));
		assertThat(typeDefenses2.getGround(), equalTo(200));
		
		assertThat(typeDefenses2.getFlying(), equalTo(50));
		assertThat(typeDefenses2.getPsychic(), equalTo(100));
		assertThat(typeDefenses2.getBug(), equalTo(100));
		assertThat(typeDefenses2.getRock(), equalTo(100));
		assertThat(typeDefenses2.getGhost(), equalTo(100));
		assertThat(typeDefenses2.getDragon(), equalTo(100));
		assertThat(typeDefenses2.getDark(), equalTo(100));
		assertThat(typeDefenses2.getSteel(), equalTo(50));
		assertThat(typeDefenses2.getFairy(), equalTo(100));
	}
	
}

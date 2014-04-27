package com.pokescrape.out;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pokescrape.data.Forme;
import com.pokescrape.data.PokedexData;
import com.pokescrape.data.Pokemon;

public class JsonPokemonOutputterTest {
	
	private static final String TEST_DIR = "testJson";
	
	private File testDir;
	
	private JsonPokemonOutputter jsonOutputter;
	
	@Before
	public void setUp() {
		testDir = new File(TEST_DIR);
		if(!testDir.exists()) {
			testDir.mkdir();
		}
		jsonOutputter = new JsonPokemonOutputter();
		jsonOutputter.setBaseOutputDir(TEST_DIR);
	}
	
	@After
	public void tearDown() {
		if(testDir.exists()) {
			for (File file : testDir.listFiles()) {
		        file.delete();
		    }
			testDir.delete();
		}
	}

	@Test
	public void testFileName() throws Exception {
		Pokemon pokemon = generatePokemon();
		
		jsonOutputter.output(pokemon);
		
		File [] jsonFiles = testDir.listFiles();
		
		assertThat(jsonFiles, arrayWithSize(1));
		assertThat(jsonFiles[0].getName(), equalTo("001-Testamon.json"));
	}
	
	@Test
	public void testFileNameSpaces() throws Exception {
		Pokemon pokemon = generatePokemon();
		pokemon.setName("Ultra Test Mon");
		pokemon.getForms()[0].getPokedexData().setNationalDexNumber(314);
		
		jsonOutputter.output(pokemon);
		
		File [] jsonFiles = testDir.listFiles();
		
		assertThat(jsonFiles, arrayWithSize(1));
		assertThat(jsonFiles[0].getName(), equalTo("314-UltraTestMon.json"));
	}
	
	@Test
	public void testFileNameInvalidChars() throws Exception {
		Pokemon pokemon = generatePokemon();
		pokemon.setName("Farfetch'd");
		pokemon.getForms()[0].getPokedexData().setNationalDexNumber(77);
		
		jsonOutputter.output(pokemon);
		
		File [] jsonFiles = testDir.listFiles();
		
		assertThat(jsonFiles, arrayWithSize(1));
		assertThat(jsonFiles[0].getName(), equalTo("077-Farfetchd.json"));
	}
	
	@Test
	public void testContentsAreJson() throws Exception {
		Pokemon pokemon = generatePokemon();
		
		jsonOutputter.output(pokemon);
		
		File [] jsonFiles = testDir.listFiles();
		
		assertThat(jsonFiles, arrayWithSize(1));

		assertThat(getFileContent(jsonFiles[0]), 
				equalTo("{\"name\":\"Testamon\",\"forms\":[{\"pokedexData\":{\"nationalDexNumber\":1}}]}"));
	}
	
	@Test //that the file is overridden each time
	public void testFileOveride() throws Exception {
		Pokemon pokemon = generatePokemon();
		
		jsonOutputter.output(pokemon);
		
		File [] jsonFiles = testDir.listFiles();
		
		assertThat(jsonFiles, arrayWithSize(1));
		assertThat(jsonFiles[0].getName(), equalTo("001-Testamon.json"));
		assertThat(getFileContent(jsonFiles[0]), 
				equalTo("{\"name\":\"Testamon\",\"forms\":[{\"pokedexData\":{\"nationalDexNumber\":1}}]}"));
		
		pokemon.getForms()[0].setName("Test1");
		jsonOutputter.output(pokemon);
		jsonFiles = testDir.listFiles();
		assertThat(jsonFiles, arrayWithSize(1));
		assertThat(jsonFiles[0].getName(), equalTo("001-Testamon.json"));
		assertThat(getFileContent(jsonFiles[0]), 
				equalTo("{\"name\":\"Testamon\",\"forms\":[{\"name\":\"Test1\","
						+ "\"pokedexData\":{\"nationalDexNumber\":1}}]}"));
		
	}
	
	private String getFileContent(File toGet) throws FileNotFoundException {
		Scanner scanner = new Scanner(toGet);
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return content;
	}
	
	private Pokemon generatePokemon() {
		Pokemon poke = new Pokemon();
		poke.setName("Testamon");
		
		Forme testForm = new Forme();
		PokedexData pokeData = new PokedexData();
		poke.setForms(new Forme [] {testForm});
		
		pokeData.setNationalDexNumber(1);
		testForm.setPokedexData(pokeData);
		
		return poke;
	}
}

package com.pokescrape.out;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokescrape.data.Pokemon;

@Component
public class JsonPokemonOutputter implements PokemonOutputter {
	
	private String baseOutputDir;
	
	@Value("json")
	public void setBaseOutputDir(String baseOutputDir) {
		this.baseOutputDir = baseOutputDir;
	}

	@Override
	public void output(Pokemon toOutput) throws IOException {
		createOutputDirIfNotExists();
		
        String fileName = String.format("%s/%03d-%s", baseOutputDir, 
        		toOutput.getForms()[0].getPokedexData().getNationalDexNumber(), 
        		toOutput.getName().replaceAll("[^A-Za-z0-9]", ""));
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL); //Don't add to JSON if value is null
        mapper.writeValue(new File(fileName + ".json"), toOutput);
	}
	
	private void createOutputDirIfNotExists() {
		File testDir = new File(baseOutputDir);
		if(!testDir.exists()) {
			testDir.mkdir();
		}
	}
}

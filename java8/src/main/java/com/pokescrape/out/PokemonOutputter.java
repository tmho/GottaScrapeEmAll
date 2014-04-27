package com.pokescrape.out;

import java.io.IOException;

import com.pokescrape.data.Pokemon;

public interface PokemonOutputter {

	void output(Pokemon toOutput) throws IOException;
}

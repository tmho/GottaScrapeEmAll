package com.pokescrape;

import java.util.List;

import javax.annotation.Resource;

import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.pokescrape.fetch.PokemonScraper;
import com.pokescrape.fetch.UrlFetcher;
import com.pokescrape.out.PokemonOutputter;

@Component
public class Main {
	
	private static final String NATIONAL_DEX_URL = "http://pokemondb.net/pokedex/national";
	
	private int delayBetweenScrapes;
	
	@Resource
	private UrlFetcher nationalDexUrlFetcher;
	
	@Resource
	private PokemonScraper pokemonScraper;
	
	@Resource
	private PokemonOutputter jsonPokemonOutputter;

	@Value("1000")
	public void setDelayBetweenScrapes(int delayBetweenScrapes) {
		this.delayBetweenScrapes = delayBetweenScrapes;
	}

	public static void main(String[] args) {
		System.out.println("------------------------------");
		System.out.println("--- Gotta Scrape them all! ---");
		System.out.println("------------------------------\n");
		
		ClassPathXmlApplicationContext context = 
		    	   new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		
		Main mainRunner = (Main)context.getBean("main");
		mainRunner.run();
		
		context.close();
	}
	
	public void run() {
		List<String> pokemonUrls;
		try {
			pokemonUrls = nationalDexUrlFetcher.fetch(NATIONAL_DEX_URL);
			System.out.println("Pokemon found in national dex: " + pokemonUrls.size());
			System.out.print("Scraping:");
			for(String url : pokemonUrls) {
				Thread.sleep(delayBetweenScrapes); //Need to delay to prevent appearing as a DOS attack
				scrapeUrl(url);
				System.out.print(".");
			}
			System.out.println("Done!");
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	private void scrapeUrl(String url) {
		boolean scrape503 = false;
		do {
			try {
				jsonPokemonOutputter.output(pokemonScraper.scrape(url));
				scrape503 = false;
			} 
			catch(HttpStatusException hse) {
				if(hse.getStatusCode() == 503) { //Try again
					scrape503 = true;
				} else {
					System.out.println("\nFailed to scrape: " + url);
					System.err.println(hse);
				}
			} 
			catch(Exception e) {
				System.out.println("\nFailed to scrape: " + url);
				System.err.println(e);
			}
		} while((scrape503));
	}
}

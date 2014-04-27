package com.pokescrape.fetch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)  
public class NationalDexUrlFetcherTest {

	@Mock
	private DocumentFetcher documentFetcher;
	
	@InjectMocks
	private NationalDexUrlFetcher nationalDexUrlFetcher;
	
	@Test
	public void testFetch() throws Exception {
		String baseUrl = "http://pokescrape.com";

		Document mockDoc = Jsoup.parse(new File(
				this.getClass().getClassLoader().getResource("national.html").getPath()), "UTF-8", baseUrl);
		when(documentFetcher.fetch(anyString())).thenReturn(mockDoc);
		
		List<String> urls = nationalDexUrlFetcher.fetch("fakeUrl");
		
		assertThat(urls.size() > 0, equalTo(true));
		assertThat(urls.get(0), equalTo(baseUrl + "/pokedex/bulbasaur"));
		assertThat(urls.get(5), equalTo(baseUrl + "/pokedex/charizard"));
		assertThat(urls.get(695), equalTo(baseUrl + "/pokedex/tyrunt"));
	}
}

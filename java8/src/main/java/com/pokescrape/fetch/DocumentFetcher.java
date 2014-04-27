package com.pokescrape.fetch;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentFetcher {

	public Document fetch(String url) throws IOException {
		return Jsoup.connect(url).get();
	}
	
}

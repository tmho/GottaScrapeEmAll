package com.pokescrape.fetch;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class NationalDexUrlFetcher implements UrlFetcher {
	
	@Resource
	private DocumentFetcher documentFetcher;

	@Override
	public List<String> fetch(String url) throws IOException {
		Document nationalDexDoc = documentFetcher.fetch(url);
		
		return nationalDexDoc.select(".ent-name").stream().map(e -> e.attr("abs:href")).collect(Collectors.toList());
	}
	
}

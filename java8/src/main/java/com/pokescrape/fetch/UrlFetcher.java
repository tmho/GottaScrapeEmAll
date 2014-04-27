package com.pokescrape.fetch;

import java.io.IOException;
import java.util.List;

public interface UrlFetcher {
	List<String> fetch(String url) throws IOException;
}
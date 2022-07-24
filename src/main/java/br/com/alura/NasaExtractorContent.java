package br.com.alura;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class NasaExtractorContent implements ContentExtractor {
	public List<Content> extractContents(String json) {

		var jsonArray = new JSONArray(json);
		
		List<Content> contents = new ArrayList<>();

		// Populate list of content
		for (int i = 0; i < jsonArray.length(); i++) {
			String title = (String) jsonArray.getJSONObject(i).get("title");
			String urlImage = (String) jsonArray.getJSONObject(i).get("url");
			var content = new Content(title, urlImage);
			
			contents.add(content);
		}
		
		return contents;
	}
}

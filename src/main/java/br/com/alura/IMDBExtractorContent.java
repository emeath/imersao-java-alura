package br.com.alura;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class IMDBExtractorContent implements ContentExtractor {
	
	private String imgBaseUrl = "https://image.tmdb.org/t/p/w500";;

	public List<Content> extractContents(String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONArray jsonArray = jsonObject.getJSONArray("results");

		List<Content> contents = new ArrayList<>();

		// Populate list of content

		for (int i = 0; i < jsonArray.length(); i++) {
			String title = (String) jsonArray.getJSONObject(i).get("original_name");
			String urlImage = imgBaseUrl + jsonArray.getJSONObject(i).get("poster_path");
			var content = new Content(title, urlImage);

			contents.add(content);
		}
		return contents;
	}
}

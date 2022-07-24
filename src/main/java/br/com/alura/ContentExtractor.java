package br.com.alura;

import java.util.List;

public interface ContentExtractor {

	public List<Content> extractContents(String json);
}

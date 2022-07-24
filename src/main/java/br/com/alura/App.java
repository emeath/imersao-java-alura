package br.com.alura;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BLUE_BACK;
import static com.diogonunes.jcolor.Attribute.BOLD;
import static com.diogonunes.jcolor.Attribute.CYAN_TEXT;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;
import static com.diogonunes.jcolor.Attribute.YELLOW_BACK;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.diogonunes.jcolor.Attribute;

public class App {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// IMDB
		// read api key from environment variable
		Map<String, String> env = System.getenv();
		String apiKeyMatheus = env.get("APIKEYMATHEUS");
		// make HTTP conection and search tv shows to discover
		String baseUrl = "https://api.themoviedb.org/3";
		String endpoint = "/discover/tv";
		String requiredParam = "?api_key=" + apiKeyMatheus;
		String optionalParam = "&air_date.lte=2000";
		endpoint += requiredParam + optionalParam;
		String url = baseUrl + endpoint;
		ContentExtractor extractor = new IMDBExtractorContent();

//		NASA
//		String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-04-25&end_date=2022-04-27";
//		ContentExtractor extractor = new NasaExtractorContent();

		var http = new ClientHTTP();
		String json = http.fetchData(url);

		List<Content> contents = extractor.extractContents(json);

		Attribute[] myFormat = new Attribute[] { RED_TEXT(), YELLOW_BACK(), BOLD() };
		Attribute[] myOtherFormat = new Attribute[] { GREEN_TEXT(), BLUE_BACK(), BOLD() };

		System.out.println(colorize("                 ", myOtherFormat));
		System.out.println(colorize("   - Discover -  ", myOtherFormat));
		System.out.println(colorize("                 ", myOtherFormat));
		System.out.println(colorize("\n", myOtherFormat));

		StickerGenerator stickerGenerator = new StickerGenerator();

		for (int i = 0; i < 3; i++) {

			Content content = contents.get(i);

			InputStream inputStream = new URL(content.getUrlImage()).openStream();

			stickerGenerator.create(inputStream, content.getTitle() + ".png");

			System.out.println(colorize(content.getTitle(), myFormat));
			System.out.println(colorize(content.getUrlImage(), CYAN_TEXT()));
			System.out.println("\n");
		}

	}
}

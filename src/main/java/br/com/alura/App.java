package br.com.alura;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BLUE_BACK;
import static com.diogonunes.jcolor.Attribute.BOLD;
import static com.diogonunes.jcolor.Attribute.CYAN_TEXT;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;
import static com.diogonunes.jcolor.Attribute.YELLOW_BACK;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import org.json.JSONObject;

import com.diogonunes.jcolor.Attribute;

public class App {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        boolean debug = false;

        // read api key from environment variable
        Map<String, String> env = System.getenv();
        String apiKeyMatheus = env.get("APIKEYMATHEUS");

        // make HTTP conection and search tv shows to discover
        String baseUrl = "https://api.themoviedb.org/3";
        String imgBaseUrl = "https://image.tmdb.org/t/p/w500";
        String endpoint = "/discover/tv";
        String requiredParam = "?api_key=" + apiKeyMatheus;
        String optionalParam = "&air_date.lte=2000";
        endpoint += requiredParam + optionalParam;
        String url = baseUrl + endpoint;

        URI address = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(address).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        if (debug) {
            System.out.println(body);
            System.exit(-1);
        }

        JSONObject jsonObject = new JSONObject(body);
        
        Attribute[] myFormat = new Attribute[]{RED_TEXT(), YELLOW_BACK(), BOLD()};
        Attribute[] tvShowsTitle = new Attribute[]{GREEN_TEXT(), BLUE_BACK(), BOLD()};
        
        System.out.println(colorize("                 ", tvShowsTitle));
        System.out.println(colorize("Discover Tv Shows", tvShowsTitle));
        System.out.println(colorize("                 ", tvShowsTitle));
        System.out.println(colorize("\n", tvShowsTitle));
        
        
        StickerGenerator stickerGenerator = new StickerGenerator();
        for (int i = 0; i < jsonObject.getJSONArray("results").length(); i++) {
            
        	String tvShowName = (String) jsonObject.getJSONArray("results").getJSONObject(i).get("original_name");
            String rating = jsonObject.getJSONArray("results").getJSONObject(i).get("vote_average").toString();          
            String urlPosterImage = imgBaseUrl + jsonObject.getJSONArray("results").getJSONObject(i).get("poster_path");
            
            InputStream inputStream = new URL(urlPosterImage).openStream();
            stickerGenerator.create(inputStream, tvShowName + ".png", rating);
            
            System.out.println(colorize(tvShowName, myFormat));
            
            if (Math.round(Double.parseDouble(rating)) == 0) {
            	for (int j = 0; j < 10; j++) {
                	System.out.print("🍅");
    			}
			} else {
				for (int j = 0; j < Math.round(Double.parseDouble(rating)); j++) {
	            	System.out.print("⭐");
				}
			}
                       
            System.out.println("(" + rating + ")");
            System.out.println(colorize(urlPosterImage, CYAN_TEXT()));
            System.out.println("\n\n");
        }
        
        // make HTTP conection and search trending tv shows
        endpoint = "/trending/tv/week";
        optionalParam = "";
        endpoint += requiredParam + optionalParam;
        url = baseUrl + endpoint;
        
        address = URI.create(url);
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder(address).GET().build();
        response = client.send(request, BodyHandlers.ofString());
        body = response.body();

        JSONObject jsonObjectTredingTvShows = new JSONObject(body);
		
        if (debug) {
            System.out.println(body);
            System.exit(-1);
        }

        System.out.println(colorize("                 ", tvShowsTitle));
        System.out.println(colorize("Trending Tv Shows", tvShowsTitle));
        System.out.println(colorize("                 ", tvShowsTitle));
        System.out.println(colorize("\n", tvShowsTitle));
        
        for (int i = 0; i < jsonObjectTredingTvShows.getJSONArray("results").length(); i++) {
            
        	String tvShowName = (String) jsonObjectTredingTvShows.getJSONArray("results").getJSONObject(i).get("original_name");
            String rating = jsonObjectTredingTvShows.getJSONArray("results").getJSONObject(i).get("vote_average").toString();          
            String urlPosterImage = imgBaseUrl + jsonObjectTredingTvShows.getJSONArray("results").getJSONObject(i).get("poster_path");
        	
            InputStream inputStream = new URL(urlPosterImage).openStream();
            stickerGenerator.create(inputStream, tvShowName + ".png", rating);
         
            System.out.println(colorize(tvShowName, myFormat));
            
            if (Math.round(Double.parseDouble(rating)) == 0) {
            	for (int j = 0; j < 10; j++) {
                	System.out.print("🍅");
    			}
			} else {
				for (int j = 0; j < Math.round(Double.parseDouble(rating)); j++) {
	            	System.out.print("⭐");
				}
			}
                       
            System.out.println("(" + rating + ")");
            System.out.println(colorize(urlPosterImage, CYAN_TEXT()));
            System.out.println("\n\n");
        } // fim aula 2 :)

    }
}

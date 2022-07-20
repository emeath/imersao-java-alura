import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

import java.util.Map;

public class App {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        Map<String, String> env = System.getenv();

        //String apiKeyMatheus = "9ae1ce85be5728e80caaf9bd1a14226d";

        String apiKeyMatheus = env.get("APIKEYMATHEUS");

        System.out.println(apiKeyMatheus);

        // make HTTP conection and search 250 top movies
        String baseUrl = "https://api.themoviedb.org/3";
        String url = baseUrl + "/movie/76341?api_key=" + apiKeyMatheus;



        URI address = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(address).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        //System.out.println(body);
        JSONObject jsonObject = new JSONObject(body);
        System.out.println(jsonObject.get("original_title"));
        System.out.println(jsonObject.get("poster_path"));
        System.out.println(jsonObject.get("vote_average"));

        //titulo, imagem, classificacao
        //System.out.println(jsonObject.get("title"));
    }
}


//https://api.themoviedb.org/3/discover/movie?api_key=paodequeijo&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate

/*
 * https://www.themoviedb.org/documentation/api
 * https://www.themoviedb.org/documentation/api/wrappers-libraries
 * https://www.themoviedb.org/settings/api
 * https://developers.themoviedb.org/3/getting-started/introduction
 * https://www.themoviedb.org/documentation/api
 * 
 */

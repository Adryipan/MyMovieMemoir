package com.example.mymoviemenoir.neworkconnection;

import com.example.mymoviemenoir.entity.Movie;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchOMDbAPI {
    private static final String apiKey = "4526d397";
    private static final String BASE_URL =  "http://www.omdbapi.com/?apikey=" + apiKey + "&";

    public static String searchByIMDbID (String imdbID){
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + "i=" + imdbID);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Movie getResultMovie(String result){

        //extract the info of the movie
        try{
            JSONObject jsonObject = new JSONObject(result);
            String movieName = jsonObject.getString("Title");
            String rating = jsonObject.getString("imdbRating");
            String cast = jsonObject.getString("Actors");
            String releaseDate = jsonObject.getString("Released");
            String country = jsonObject.getString("Country");
            String plot = jsonObject.getString("Plot");
            String genre = jsonObject.getString("Genre");
            String director = jsonObject.getString("Director");
            String imdbID = jsonObject.getString("imdbID");
            Movie thisMovie = new Movie(movieName, rating, genre, cast, releaseDate, country, director, plot, imdbID);
            return thisMovie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

package com.example.mymoviemenoir.neworkconnection;

import com.example.mymoviemenoir.entity.Movie;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchOMDbAPI {

//    private static final String apiKey = "";
//    private static final String BASE_URL =  "http://www.omdbapi.com/?apikey=" + apiKey + "&";

    public static native String omdbBaseUrl();
    static {
        System.loadLibrary("native-lib");
    }

    private static final String BASE_URL =  omdbBaseUrl();


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
            float convertedOnlineRating = 0;
            if(!jsonObject.getString("imdbRating").equals("N/A")) {
                Double rating = jsonObject.getDouble("imdbRating");
                rating.floatValue();
                if (rating >= 9.1f) {
                    convertedOnlineRating = 5f;
                } else if (rating >= 8.2f && rating <= 9f) {
                    convertedOnlineRating = 4.5f;
                } else if (rating >= 7.3f && rating <= 9.1f) {
                    convertedOnlineRating = 4f;
                } else if (rating >= 6.4f && rating <= 7.2f) {
                    convertedOnlineRating = 3.5f;
                } else if (rating >= 5.5f && rating <= 6.3f) {
                    convertedOnlineRating = 3f;
                } else if (rating >= 4.6f && rating <= 5.4f) {
                    convertedOnlineRating = 2.5f;
                } else if (rating >= 3.7f && rating <= 4.5f) {
                    convertedOnlineRating = 2f;
                } else if (rating >= 2.8f && rating <= 3.6f) {
                    convertedOnlineRating = 1.5f;
                } else if (rating >= 1.9f && rating <= 2.7f) {
                    convertedOnlineRating = 1f;
                } else if (rating >= 1f && rating <= 1.8f) {
                    convertedOnlineRating = 0.5f;
                }
            }

            String cast = jsonObject.getString("Actors");
            String releaseDate = jsonObject.getString("Released");
            String country = jsonObject.getString("Country");
            String plot = jsonObject.getString("Plot");
            String genre = jsonObject.getString("Genre");
            String director = jsonObject.getString("Director");
            String imdbID = jsonObject.getString("imdbID");
            String imageLink = jsonObject.getString("Poster");
            Movie thisMovie = new Movie(movieName, String.valueOf(convertedOnlineRating), genre, cast, releaseDate, country, director, plot, imdbID, imageLink);
            return thisMovie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPosterLink(String result){
        String imageLink = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            imageLink = jsonObject.getString("Poster");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageLink;
    }

    public static String getReleaseDate(String result){
        String releaseDate = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            releaseDate = jsonObject.getString("Released");
        } catch (Exception e) {
            releaseDate = "N/A";
            e.printStackTrace();
        }
        return releaseDate;
    }

    public static String getIMDBRating(String result){
        String rating = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            rating = jsonObject.getString("imdbRating");
        } catch (Exception e) {
            rating = "0";
            e.printStackTrace();
        }
        return rating;
    }

    public static String getGenre(String result){
        String genre = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            genre = jsonObject.getString("Genre");
        } catch (Exception e) {
            genre = "N/A";
            e.printStackTrace();
        }
        return genre;
    }

    public static String getCountry(String result){
        String country = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            country = jsonObject.getString("Country");
        } catch (Exception e) {
            country = "N/A";
            e.printStackTrace();
        }
        return country;
    }

    public static String getDirector (String result){
        String director = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            director = jsonObject.getString("Director");
        } catch (Exception e) {
            director = "N/A";
            e.printStackTrace();
        }
        return director;
    }

    public static String getPlot(String result){
        String plot = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            plot = jsonObject.getString("Plot");
        } catch (Exception e) {
            plot = "N/A";
            e.printStackTrace();
        }
        return plot;
    }

    public static String getCast(String result){
        String cast = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            cast = jsonObject.getString("Actors");
        } catch (Exception e) {
            cast = "N/A";
            e.printStackTrace();
        }
        return cast;
    }

}

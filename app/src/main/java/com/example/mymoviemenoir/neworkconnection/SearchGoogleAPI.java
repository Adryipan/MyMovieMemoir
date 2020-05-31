package com.example.mymoviemenoir.neworkconnection;

import com.example.mymoviemenoir.model.SearchMovieResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchGoogleAPI {

    private static final String API_KEY = "AIzaSyDXj_I356VSdi8hrhVoMKRgW4LA6YD74vQ";
    private static final String SEARCH_ID_cx = "008298406434288315023:igmlig8ugzm";
    private static final String BASE_URL = "https://www.googleapis.com/customsearch/v1?key="+
                                            API_KEY + "&cx=" + SEARCH_ID_cx + "&q=";

    public static String search(String keyword, String[] params, String[] values){
        String result = "";
        keyword = keyword.replace(" ", "+");
        OkHttpClient client = new OkHttpClient();
        String query_parameter="";
        if(params != null && values != null){
            for(int i = 0; i < params.length; i++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + keyword + query_parameter);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            result = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            if(jsonArray!= null && jsonArray.length() > 0){
                snippet = jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "Oh no, I found nothing!";
        }

        return snippet;
    }

    public static ArrayList<SearchMovieResult> getNameYearImage(String result){
        ArrayList<SearchMovieResult> movieList = new ArrayList<SearchMovieResult>();
        //extract the item array (Only add imdb result for the moment because they format their result well)
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    //Extract from metatags seems easier
                    JSONObject metaTag = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0);
                    //Only look at IMDB
                    if(metaTag.getString("og:site_name").toUpperCase().equals("IMDB")){
                        String title = metaTag.getString("title");
                        title = title.substring(0, title.length()-6);
                        //extract the name (Title of every jsonObject)
                        String movieName = title.split(Pattern.quote("("))[0].trim();

                        //extract the year (From the title of each json)
                        String releaseYear = title.split(Pattern.quote("("))[1].substring(0,4);

                        //extract image link (og.image)
                        String imageLink = metaTag.getString("og:image");

                        //extract the imdb id for the movie api
                        String imdbID = metaTag.getString("pageid");

                        SearchMovieResult thisResult = new SearchMovieResult(movieName, releaseYear, imageLink, imdbID);
                        movieList.add(thisResult);
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return movieList;
    }

    public static String getIMDbID(String result){
        String imdbID = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    //Extract from metatags seems easier
                    JSONObject metaTag = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0);
                    //Only look at IMDB
                    if (metaTag.getString("og:site_name").toUpperCase().equals("IMDB")) {
                        imdbID = metaTag.getString("pageid");
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imdbID;
    }

}

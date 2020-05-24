package com.example.mymoviemenoir.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopFiveMovieResult {
    private String name;
    private String releaseDate;
    private String rating;

    public TopFiveMovieResult(String name, String releaseDate, String rating){
        this.name = name;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public static List<TopFiveMovieResult> getResult(String queryResult){
        List<TopFiveMovieResult> results = new ArrayList<TopFiveMovieResult>();
        try {
            JSONArray jsonArray = new JSONArray(queryResult);
            int length = jsonArray.length();
            if(length != 0){
                for(int i = 0; i < length; i++){
                    JSONObject thisObject = jsonArray.getJSONObject(i);
                    String movieName = thisObject.getString("Movie Name");
                    String date = thisObject.getString("Release Date");
                    String rating = thisObject.getString("Rating");
                    TopFiveMovieResult thisResult = new TopFiveMovieResult(movieName, date, rating);
                    results.add(thisResult);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

}

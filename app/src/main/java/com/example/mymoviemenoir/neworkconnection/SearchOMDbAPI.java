package com.example.mymoviemenoir.neworkconnection;

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
}

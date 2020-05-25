package com.example.mymoviemenoir;

import android.view.textclassifier.TextLinks;

import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

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
}

package com.example.mymoviemenoir.neworkconnection;


import com.example.mymoviemenoir.securitywidget.HashingFunction;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client = null;
    private String results;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection(){
        client = new OkHttpClient();
    }

    private static final String BASE_URL = "http://10.0.2.2:15321/MyMovieMemoir/webresources/";
    private static final String RESOURCE_PERSON = "moviemenoirws.person/";
    private static final String RESOURCE_CINEMA = "moviemenoirws.cinema/";
    private static final String RESOURCE_CREDENTIAL = "moviemenoirws.credentials/";
    private static final String RESOURCE_MEMOIR = "moviemenoirws.memoir/";


    public String getByAuthentication(String username, String password){
        String hashed = HashingFunction.getHash(password);
        final String methodPath = RESOURCE_CREDENTIAL + "findByAuthentication/" + username + "/" + hashed;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();

        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getPersonNameById(String userId){
        final String methodPath = RESOURCE_PERSON + userId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String firstname = "";
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
            JSONObject json = new JSONObject(results);
            firstname = json.getString("firstName");
        }catch (Exception e){
            e.printStackTrace();
        }
        return firstname;
    }

    public String topFiveMovieOfTheYear(String userId){
        final String methodPath = RESOURCE_MEMOIR + "topFiveMovieOfTheYear/" + userId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        return results;
    }

}

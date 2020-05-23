package com.example.mymoviemenoir.neworkconnection;


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

    private static final String BASE_URL = "http://10.0.0.2:15321/MyMovieMenoir/websources/";
    private static final String RESOURCE_PERSON = "moviememoirws.person/";
    private static final String RESOURCE_CINEMA = "moviememoirws.cinema/";
    private static final String RESOURCE_CREDENTIAL = "moviememoirws.credentials/";
    private static final String RESOURCE_MEMOIR = "moviememoirws.memoir/";


    public String getCredemtialByUsername(String username){
        final String methodPath = RESOURCE_CREDENTIAL + username;
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

}

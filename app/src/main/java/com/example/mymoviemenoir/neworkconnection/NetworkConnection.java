package com.example.mymoviemenoir.neworkconnection;


import com.example.mymoviemenoir.entity.Credentials;
import com.example.mymoviemenoir.entity.Person;
import com.example.mymoviemenoir.securitywidget.HashingFunction;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

    public String getByUsername(String email){
        final String methodPath = RESOURCE_CREDENTIAL + "findByUsername/" + email;
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

    public String addUser(String[] details){
        Person user = new Person(details[0], details[1],details[2], details[3], details[4], details[5], details[6], details[7]);
        //Construct the json
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        String strResponse = "";
        final String methodPath = RESOURCE_PERSON;
        RequestBody body = RequestBody.create(userJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try{
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }

        return strResponse;
    }

    public String addCredentials(String[] details){
        Credentials credentials = new Credentials(details[0],details[1],details[2], details[3]);

        //Construct the json
        Gson gson = new Gson();
        String userJson = gson.toJson(credentials);
        String strResponse = "";
        final String methodPath = RESOURCE_PERSON;
        RequestBody body = RequestBody.create(userJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try{
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }

        return strResponse;

    }

}

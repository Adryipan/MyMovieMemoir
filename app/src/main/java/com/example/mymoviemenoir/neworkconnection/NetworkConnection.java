package com.example.mymoviemenoir.neworkconnection;


import com.example.mymoviemenoir.entity.Cinema;
import com.example.mymoviemenoir.entity.Credentials;
import com.example.mymoviemenoir.entity.Memoir;
import com.example.mymoviemenoir.entity.Person;
import com.example.mymoviemenoir.entity.PersonWithId;
import com.example.mymoviemenoir.securitywidget.HashingFunction;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

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


    //GET queries
    public String getUserByID(String uid){
        final String methodPath = RESOURCE_PERSON + uid;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

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

    public String getByFullNamePostcode(String firstName, String surname, String postcode){
        final String methodPath = RESOURCE_PERSON + "findByFullNamePostcode/" + firstName + "/" + surname + "/" + postcode;
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

    public String getCinemaByPostcode(String cinemaPostcode){
        final String methodPath = RESOURCE_CINEMA + "findByPostcode/" + cinemaPostcode;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    //POST queries
    public String addCredentials(String[] details){
        Credentials credentials = null;
        try{
            getByFullNamePostcode(details[4], details[5], details[6]);
            JSONArray jsonArray = new JSONArray(results);
            JSONObject json = jsonArray.getJSONObject(0);
            PersonWithId thisPerson = new PersonWithId(json.getString("firstName"),
                    json.getString("surname"), json.getString("gender"), json.getString("dob"),
                    json.getString("streetAddress"), json.getString("postcode"), json.getString("stateCode"), json.getString("userId"));
            credentials = new Credentials(thisPerson, details[1],details[2], details[3]);
        }catch (Exception e){
            e.printStackTrace();
        }
        //Construct the json
        Gson gson = new Gson();
        String userJson = gson.toJson(credentials);
        String strResponse = "";
        final String methodPath = RESOURCE_CREDENTIAL;
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

    public String addUser(String[] details){
        Person user = new Person(details[0], details[1], details[2], details[3], details[4], details[5], details[6]);
        //Construct the json
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        String strResponse = "";
        final String methodPath = RESOURCE_PERSON;
        RequestBody body = RequestBody.create(userJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        String id = "";
        try{
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
            //Get userId for credential table
            getByFullNamePostcode(details[0], details[1], details[5]);
            JSONArray jsonArray = new JSONArray(results);
            JSONObject json = jsonArray.getJSONObject(0);
            id = json.getString("userId");
        }catch (Exception e){
            e.printStackTrace();
        }
        //Add to credentials
        String[] credentials = {id, details[7], details[8], details[9], details[0], details[1], details[5]};
        addCredentials(credentials);

        return strResponse;
    }

    public String addMemoir(String[] details){
        String userResult = getUserByID(details[7]);
        String cinemaResult = getCinemaByPostcode(details[6]);
        String strResponse = "";
        try{
            //Convert the user to object
            PersonWithId user;
            JSONObject userJson = new JSONObject(userResult);
            user = new PersonWithId(userJson.getString("firstName"),
                    userJson.getString("surname"),
                    userJson.getString("gender"),
                    userJson.getString("dob"),
                    userJson.getString("streetAddress"),
                    userJson.getString("postcode"),
                    userJson.getString("stateCode"),
                    userJson.getString("userId"));

            //Convert the cinema to object
            Cinema cinema;
            JSONArray cinemaJsonArray = new JSONArray(cinemaResult);
            JSONObject cinemaJson = cinemaJsonArray.getJSONObject(0);
            cinema = new Cinema(cinemaJson.getString("cinemaId"),
                    cinemaJson.getString("cinemaName"),
                    cinemaJson.getString("postcode"));

            //Create a memoir object
            Memoir memoir = new Memoir(details[0], details[1], details[2], details[3], details[4], details[5], cinema, user);
            //Create the jsonObject
            Gson gson = new Gson();
            String memoirJson = gson.toJson(memoir);
            //Build the path
            final String methodPath = RESOURCE_MEMOIR;
            RequestBody body = RequestBody.create(memoirJson, JSON);
            Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }
}

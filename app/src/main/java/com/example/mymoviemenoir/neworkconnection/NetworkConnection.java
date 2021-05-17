package com.example.mymoviemenoir.neworkconnection;


import com.example.mymoviemenoir.entity.Cinema;
import com.example.mymoviemenoir.entity.Credentials;
import com.example.mymoviemenoir.entity.MapCinema;
import com.example.mymoviemenoir.entity.Memoir;
import com.example.mymoviemenoir.entity.Person;
import com.example.mymoviemenoir.entity.PersonWithId;
import com.example.mymoviemenoir.model.MemoirResult;
import com.example.mymoviemenoir.securitywidget.HashingFunction;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client = null;
    private String results;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static native String mmmBaseUrl();
    static {
        System.loadLibrary("native-lib");
    }

    public NetworkConnection(){
        client = new OkHttpClient();
    }

    private static final String BASE_URL = mmmBaseUrl();
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

    public String getCinemaBySuburb(String cinemaSuburb){
        final String methodPath = RESOURCE_CINEMA + "findBySuburb/" + cinemaSuburb;
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

    //Count the number of watch per postcode within the start and end date
    //Date accepted format dd-mm-yyyy
    public String findByIdDate(String uid, String startDate, String endDate){
        final String methodPath = RESOURCE_MEMOIR + "findByIdDate/" + uid + "/" + startDate + "/" + endDate;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public String countMoviePerMonth(String uid, String year){
        final String methodPath = RESOURCE_MEMOIR + "countMoviePerMonth/" + uid + "/" + year;
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

    //Get all cinema from database
    public String getAllCinema(){
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + RESOURCE_CINEMA);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //Get all memoir and return list of MemoirResult
    public List<MemoirResult> getAllMemoir(String uid){
        ArrayList<MemoirResult> memoirResults = new ArrayList<>();
        //Get all memoir from server
        final String methodPath = RESOURCE_MEMOIR + "findByUID/" + uid;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try{
            Response response = client.newCall(request).execute();
            results = response.body().string();
            JSONArray jsonArray = new JSONArray(results);
            int numberOfItems = jsonArray.length();
            if(numberOfItems > 0) {
                for (int i = 0; i < numberOfItems; i++) {
                    JSONObject thisMovie = jsonArray.getJSONObject(i);
                    //Call SearchGoogle for the imdb id
                    String googleResult = SearchGoogleAPI.search(thisMovie.getString("movieName"), new String[]{"num"}, new String[]{"5"});
                    String imdbID = SearchGoogleAPI.getIMDbID(googleResult);

                    //Call OMDb for image, releaseDate and Imdb rating (convert to stars)
                    String omdbResult = SearchOMDbAPI.searchByIMDbID(imdbID);
                    String imgLink = SearchOMDbAPI.getPosterLink(omdbResult);
                    String releaseDate = SearchOMDbAPI.getReleaseDate(omdbResult);
                    String onlineRating = SearchOMDbAPI.getIMDBRating(omdbResult);
                    String genre = SearchOMDbAPI.getGenre(omdbResult);

                    // Get the release date of the movie
                    String releaseMonth = "";
                    try {
                        Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(releaseDate.split(" ")[1]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        releaseMonth = String.valueOf(calendar.get(Calendar.MONTH));
                        if (calendar.get(Calendar.MONTH) < 10) {
                            releaseMonth = "0" + String.valueOf(calendar.get(Calendar.MONTH));
                        }

                        releaseDate = releaseDate.split(" ")[2] + "-" + releaseMonth + "-" + releaseDate.split(" ")[0];

                    } catch (Exception e) {
                        releaseDate = "9999-01-01";
                        e.printStackTrace();
                    }

                    String country = SearchOMDbAPI.getCountry(omdbResult);
                    String director = SearchOMDbAPI.getDirector(omdbResult);
                    String cast = SearchOMDbAPI.getCast(omdbResult);
                    String plot = SearchOMDbAPI.getPlot(omdbResult);

                    // Convert the rating score to stars
                    float convertedOnlineRating = 0f;
                    if (!onlineRating.equals("N/A")) {
                        convertedOnlineRating = Float.parseFloat(onlineRating);
                        if (convertedOnlineRating >= 9.1f) {
                            convertedOnlineRating = 5f;
                        } else if (convertedOnlineRating >= 8.2f && convertedOnlineRating <= 9f) {
                            convertedOnlineRating = 4.5f;
                        } else if (convertedOnlineRating >= 7.3f && convertedOnlineRating <= 9.1f) {
                            convertedOnlineRating = 4f;
                        } else if (convertedOnlineRating >= 6.4f && convertedOnlineRating <= 7.2f) {
                            convertedOnlineRating = 3.5f;
                        } else if (convertedOnlineRating >= 5.5f && convertedOnlineRating <= 6.3f) {
                            convertedOnlineRating = 3f;
                        } else if (convertedOnlineRating >= 4.6f && convertedOnlineRating <= 5.4f) {
                            convertedOnlineRating = 2.5f;
                        } else if (convertedOnlineRating >= 3.7f && convertedOnlineRating <= 4.5f) {
                            convertedOnlineRating = 2f;
                        } else if (convertedOnlineRating >= 2.8f && convertedOnlineRating <= 3.6f) {
                            convertedOnlineRating = 1.5f;
                        } else if (convertedOnlineRating >= 1.9f && convertedOnlineRating <= 2.7f) {
                            convertedOnlineRating = 1f;
                        } else if (convertedOnlineRating >= 1f && convertedOnlineRating <= 1.8f) {
                            convertedOnlineRating = 0.5f;
                        }
                    }
                    memoirResults.add(new MemoirResult(thisMovie.getString("movieName"),
                            new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate),
                            new SimpleDateFormat("yyyy-MM-dd").parse(thisMovie.getString("watchDate").substring(0, 10))
                            , Float.parseFloat(thisMovie.getString("rating")),
                            convertedOnlineRating, thisMovie.getString("comment"), imgLink, thisMovie.getJSONObject("cinemaId").getString("cinemaName"),
                            thisMovie.getJSONObject("cinemaId").getString("suburb"), genre, country, director, cast, plot));
                }

            }
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }
        return memoirResults;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------

    //POST queries
    public String addCredentials(String[] details){
        Credentials credentials = null;
        try{
            getByFullNamePostcode(details[4], details[5], details[6]);
            JSONArray jsonArray = new JSONArray(results);
            JSONObject json = jsonArray.getJSONObject(0);
            PersonWithId thisPerson = new PersonWithId(json.getString("firstName"),
                    json.getString("surname"), json.getString("gender"), json.getString("dob"),
                    json.getString("streetAddress"), json.getString("postcode"), json.getString("stateCode"), json.getInt("userId"));
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
        String cinemaResult = getCinemaBySuburb(details[6]);
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
                    userJson.getInt("userId"));

            //Convert the cinema to object
            Cinema cinema;
            JSONArray cinemaJsonArray = new JSONArray(cinemaResult);
            JSONObject cinemaJson = cinemaJsonArray.getJSONObject(0);
            cinema = new Cinema(cinemaJson.getInt("cinemaId"),
                    cinemaJson.getString("cinemaName"),
                    cinemaJson.getString("suburb"));


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

    public String addCinema(String cinemaName, String suburb){
        String strResponse = "";
        String jsonString = "{ \"cinemaName\":\"" + cinemaName + "\",\"suburb\":\"" + suburb + "\" }";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + RESOURCE_CINEMA);
        try{
            RequestBody body = RequestBody.create(jsonString, JSON);
            Request request = builder.post(body).build();
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }
}

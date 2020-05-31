package com.example.mymoviemenoir.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.adapter.TopFiveRecyclerViewAdapter;
import com.example.mymoviemenoir.model.TopFiveMovieResult;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private NetworkConnection networkConnection = null;
    private View view = null;

    private List<TopFiveMovieResult> movies;
    private TopFiveRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        networkConnection = new NetworkConnection();

        //Get current day and time
        Date date = Calendar.getInstance().getTime();

        //Show the date on the screen
        SimpleDateFormat formatToday = new SimpleDateFormat("dd MMM yyyy");
        String today = formatToday.format(date);
        TextView dateTV = view.findViewById(R.id.dateTV);
        dateTV.setText(today);

        //Greet the user
        SimpleDateFormat formatNow = new SimpleDateFormat("HH");
        String nowString = formatNow.format(date);
        TextView greetingTV = view.findViewById(R.id.greetTV);
        int now = Integer.parseInt(nowString);
        if(now < 12){
            greetingTV.setText("Good morning");
        }else if(now >= 12 && now < 18){
            greetingTV.setText("Good afternoon");
        }else if(now >= 18) {
            greetingTV.setText("Good evening");
        }

        //Get the user's info
        SharedPreferences sharedPref = getActivity().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("USERID",null);

        //Get the user's name
        GetNameTask getNameTask = new GetNameTask();
        getNameTask.execute(userId);

        //Show top five movie of the year for the user
        GetTopFiveTask getTopFiveTask = new GetTopFiveTask();
        getTopFiveTask.execute(userId);

        return view;
    }

    private class GetNameTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String userId = strings[0];
            return networkConnection.getPersonNameById(userId);
        }

        @Override
        protected void onPostExecute(String name){
            try {
                TextView nameTV = view.findViewById(R.id.nameTV);
                nameTV.setText(name);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private class GetTopFiveTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String userId = strings[0];
            return networkConnection.topFiveMovieOfTheYear(userId);
        }

        @Override
        protected void onPostExecute(String result){
            try{
                recyclerView = view.findViewById(R.id.topFiveRecyclerView);
                movies = new ArrayList<TopFiveMovieResult>();
                JSONArray jsonArray = new JSONArray(result);
                int length = jsonArray.length();
                if(length > 0){
                    for(int i = 0; i < length; i++){
                        JSONObject thisMovie = jsonArray.getJSONObject(i);
                        movies.add(new TopFiveMovieResult(thisMovie.getString("Movie Name")
                                , thisMovie.getString("Release Date")
                                , thisMovie.getString("Rating")));
                    }
                    adapter = new TopFiveRecyclerViewAdapter(movies);
                    recyclerView.addItemDecoration(new DividerItemDecoration(HomeFragment.this.getContext(), LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                }

            }catch(Exception e){
                e.printStackTrace();
            }


        }
    }
}

package com.example.mymoviemenoir.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.adapter.MemoirRecyclerViewAdapter;
import com.example.mymoviemenoir.model.MemoirResult;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MemoirFragment extends Fragment {

    private NetworkConnection networkConnection = null;
    private View view = null;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<MemoirResult> memoirs;
    private MemoirRecyclerViewAdapter adapter;
    private Spinner genreSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private TextView sortByWatchDateTV;
    private TextView sortByYourRatingTV;
    private TextView sortByOnlineRatingTV;
    List<MemoirResult> filteredResult;


    public MemoirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_memoir, container, false);

        networkConnection = new NetworkConnection();
        genreSpinner = view.findViewById(R.id.genreSpinner);
        recyclerView = view.findViewById(R.id.myMemoirRV);
        layoutManager = new LinearLayoutManager(MemoirFragment.this.getContext());
        memoirs = new ArrayList<>();
        filteredResult = new ArrayList<>();


        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("USERID", Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("USERID", null);
        GetAllMemoirTask getAllMemoirTask = new GetAllMemoirTask();
        getAllMemoirTask.execute(uid);
        Toast.makeText(MemoirFragment.this.getContext(), "Loading....", Toast.LENGTH_LONG).show();

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = parent.getItemAtPosition(position).toString();

                if(selectedGenre.equals("Show all"))
                {
                    filteredResult = memoirs;
                }else{
                    for(MemoirResult thisMemoir : memoirs){
                        if(thisMemoir.getGenre().toUpperCase().trim().equals(selectedGenre.toUpperCase().trim())){
                            filteredResult.add(thisMemoir);
                        }
                    }
                }

                adapter = new MemoirRecyclerViewAdapter(filteredResult);
                recyclerView.addItemDecoration(new DividerItemDecoration(MemoirFragment.this.getContext(),
                        LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sortByWatchDateTV = view.findViewById(R.id.sortByWatchDateTV);
        sortByWatchDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MemoirResult> sortedList = filteredResult.stream()
                        .sorted(Comparator.comparing(MemoirResult::getWatchDate))
                        .collect(Collectors.toList());

                adapter = new MemoirRecyclerViewAdapter(sortedList);
                recyclerView.addItemDecoration(new DividerItemDecoration(MemoirFragment.this.getContext(),
                        LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);

            }
        });

        sortByYourRatingTV = view.findViewById(R.id.sortByMyRatingTV);
        sortByYourRatingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MemoirResult> sortedList = filteredResult.stream()
                        .sorted(Comparator.comparing(MemoirResult::getUserRating).reversed())
                        .collect(Collectors.toList());

                adapter = new MemoirRecyclerViewAdapter(sortedList);
                recyclerView.addItemDecoration(new DividerItemDecoration(MemoirFragment.this.getContext(),
                        LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);

            }
        });

        sortByOnlineRatingTV = view.findViewById(R.id.sortByOnlineRatingTV);
        sortByOnlineRatingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MemoirResult> sortedList = filteredResult.stream()
                        .sorted(Comparator.comparing(MemoirResult::getOnlineRating).reversed())
                        .collect(Collectors.toList());

                adapter = new MemoirRecyclerViewAdapter(sortedList);
                recyclerView.addItemDecoration(new DividerItemDecoration(MemoirFragment.this.getContext(),
                        LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });





        return view;
    }

    private class GetAllMemoirTask extends AsyncTask<String, Void, List<MemoirResult>>{

        @Override
        protected List<MemoirResult> doInBackground(String... strings) {
            return networkConnection.getAllMemoir(strings[0]);
        }


        @Override
        protected void onPostExecute(List<MemoirResult> memoirResults) {
            memoirs = memoirResults;
            filteredResult = memoirs;

            List<String> genre = new ArrayList<>();
            genre.add("Show all");
            List<String> allGenre = new ArrayList<>();
            //Extract all genre in the list
            for(MemoirResult thisResult : memoirs){
                 allGenre.add(thisResult.getGenre());
            }
            Set<String> uniqueGenre = new HashSet<String>(allGenre);
            for(String thisString : uniqueGenre){
                genre.add(thisString);
            }
            //For spinner
            arrayAdapter = new ArrayAdapter<String>(MemoirFragment.this.getContext(), android.R.layout.simple_spinner_dropdown_item, genre);
            genreSpinner.setAdapter(arrayAdapter);

            //For recycler view
            adapter = new MemoirRecyclerViewAdapter(memoirs);
            recyclerView.addItemDecoration(new DividerItemDecoration(MemoirFragment.this.getContext(),
                    LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(layoutManager);

        }
    }
}

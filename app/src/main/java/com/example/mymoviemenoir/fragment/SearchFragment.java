package com.example.mymoviemenoir.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.neworkconnection.SearchGoogleAPI;
import com.example.mymoviemenoir.adapter.SearchMovieRecyclerViewAdapter;
import com.example.mymoviemenoir.model.SearchMovieResult;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private View view = null;
    private Button searchButton;
    private EditText searchET;
    private SearchMovieRecyclerViewAdapter adapter;
    private RecyclerView searchRecyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public SearchFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_search, container, false);

        searchET = view.findViewById(R.id.searchET);
        searchButton = view.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String keyword = searchET.getText().toString();
                //Anonymous AsyncTask
                new AsyncTask<String, Void, String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        return SearchGoogleAPI.search(keyword, new String[]{"num"}, new String[]{"3"});
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        ArrayList<SearchMovieResult> searchMovieResults = SearchGoogleAPI.getNameYearImage(result);
                        adapter = new SearchMovieRecyclerViewAdapter(searchMovieResults, view.getContext());
                        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
                        searchRecyclerView.addItemDecoration(new DividerItemDecoration(searchRecyclerView.getContext(), LinearLayoutManager.VERTICAL));
                        searchRecyclerView.setAdapter(adapter);
                        layoutManager = new LinearLayoutManager(searchRecyclerView.getContext());
                        searchRecyclerView.setLayoutManager(layoutManager);
                    }
                }.execute();
            }
        });

        return view;
    }
}
